package ro.endava.bestmarathon.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.endava.bestmarathon.http.HttpResponse;
import ro.endava.bestmarathon.http.HttpResponseBuilder;
import ro.endava.bestmarathon.http.HttpResponseWriter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by cosmin on 3/16/14.
 * Web server simulator
 */
public class WebServer extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebServer.class);

    private ExecutorService executor;

    /**
     * Starts the web server and listens for incoming requests
     * @param port
     * @param noThreads
     * @param queueCapacity
     * @throws IOException
     */
    public void start(int port, int noThreads, int queueCapacity) throws IOException {
        initializeExecutor(noThreads, queueCapacity);
        ServerSocket s = new ServerSocket(port);
        LOGGER.info("Synchronous Web Server started and listening on port " + port + " (press CTRL-C to quit)");

        while (true) {
            submit(s.accept());
        }
    }

    /**
     * Initialize the ExecutorService to set up the number of threads which wil handle requests
     * @param noThreads
     * @param queueCapacity
     */
    private void initializeExecutor(int noThreads, int queueCapacity) {
        this.executor = new ThreadPoolExecutor(noThreads, noThreads,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(queueCapacity));
    }

    /**
     * Submit the request received to the executor.
     * The concurrent request number is limited so RejectedExecutionException is thrown
     * when the maximum is reached. This will result in 503-Resource Not Available HTTP status
     * @param s
     * @throws IOException
     */
    private void submit(Socket s) throws IOException {
        try {
            executor.submit(new RequestHandlerWorker(s));
        } catch (RejectedExecutionException e) {
            //Return 503 RESOURCE NOT AVAILABLE
            HttpResponseWriter.writeOnSocketAndClose(s, HttpResponseBuilder.buildServiceUnavailable());
            LOGGER.info("We can't support so many connections");
        } catch (Exception e) {
            //Return 500 INTERNAL SERVER ERROR
            HttpResponseWriter.writeOnSocketAndClose(s, HttpResponseBuilder.buildInternalServerError());
            LOGGER.info("Runtime error. Cause: " + e.getMessage());
        }
    }
}
