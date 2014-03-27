package ro.endava.bestmarathon.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.endava.bestmarathon.http.*;
import ro.endava.bestmarathon.service.ApplicationService;
import ro.endava.bestmarathon.utils.HttpRequestBuilder;
import ro.endava.bestmarathon.utils.HttpResponseWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * Created by cosmin on 3/16/14.
 * Worker that will handle an incoming request
 */
public class RequestHandlerWorker implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestHandlerWorker.class);

    private Socket socket;

    public RequestHandlerWorker(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            LOGGER.info("[{}] Processing request...", Thread.currentThread().getId());
            HttpRequest httpRequest = HttpRequestBuilder.buildHttpRequest(socket.getInputStream());
            HttpResponse httpResponse = new ApplicationService().buildHttpResponse(httpRequest);
            HttpResponseWriter.writeOnSocketAndClose(socket, httpResponse);
            LOGGER.info("[{}] Response returned.", Thread.currentThread().getId());
        } catch (Exception e) {
            try {
                HttpResponseWriter.writeOnSocketAndClose(socket,
                        new ApplicationService().buildInternalServerErrorResponse());
            } catch (IOException e1) {
                //
            }

            LOGGER.error("[{}] Runtime error. Cause: " + e.getMessage(), e, Thread.currentThread().getId());
        }
    }
}
