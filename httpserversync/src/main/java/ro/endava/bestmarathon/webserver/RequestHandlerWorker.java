package ro.endava.bestmarathon.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.endava.bestmarathon.http.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by cosmin on 3/16/14.
 */
public class RequestHandlerWorker implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebServer.class);

    private Socket socket;

    public RequestHandlerWorker(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            Thread.sleep(5000);
            HttpRequest httpRequest = HttpRequestBuilder.buildHttpRequest(socket.getInputStream());
            HttpResponse httpResponse = HttpResponseBuilder.buildHttpResponse(httpRequest);
            HttpResponseWriter.write(socket.getOutputStream(), httpResponse);
            socket.close();
        } catch (Exception e) {
            LOGGER.info("Runtime error. Cause: " + e.getMessage());
        }
    }

}
