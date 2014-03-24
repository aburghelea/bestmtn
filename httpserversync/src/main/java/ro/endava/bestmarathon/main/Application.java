package ro.endava.bestmarathon.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.endava.bestmarathon.webserver.WebServer;

/**
 * Created by cosmin on 3/16/14.
 */
public class Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        try {
            WebServer webServer = new WebServer();
            webServer.start(8000, 2, 1);
        } catch (Exception e) {
            LOGGER.info("Error starting synchronous web server. Cause: " + e.getMessage());
        }
    }
}
