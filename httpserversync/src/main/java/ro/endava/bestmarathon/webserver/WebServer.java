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
 */
public class WebServer extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebServer.class);

    public void start(int port, int noThreads, int queueCapacity) throws IOException {
        ServerSocket s = new ServerSocket(port);
        LOGGER.info("Synchronous Web Server listening on port " + port + " (press CTRL-C to quit)");
        ExecutorService executor = new ThreadPoolExecutor(noThreads, noThreads,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(queueCapacity));

        while (true) {
            Socket accept = s.accept();
            try {
                executor.submit(new RequestHandlerWorker(accept));
            } catch (RejectedExecutionException e) {
                //Should return 503 RESOURCE NOT AVAILABLE
                HttpResponse httpResponse = HttpResponseBuilder.buildServiceUnavailable();
                HttpResponseWriter.write(accept.getOutputStream(), httpResponse);
                LOGGER.info("We can't support so many connections. Cause: " + e.getMessage());
            } catch (Exception e) {
                //Should return 500 INTERNAL SERVER ERROR
                HttpResponse httpResponse = HttpResponseBuilder.buildInternalServerError();
                HttpResponseWriter.write(accept.getOutputStream(), httpResponse);
                LOGGER.info("Runtime error. Cause: " + e.getMessage());
            }
        }
    }
}
