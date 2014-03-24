package ro.endava.bestmarathon.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.endava.bestmarathon.webserver.WebServer;

/**
 * Created by cosmin on 3/16/14.
 * Entry point for the application
 */
public class Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private final static int NO_THREADS = 2;
    private final static int QUEUE_CAPACITY = 1;
    private final static int DEFAULT_PORT = 8000;
    public static final String PORT_SPECIFIER_ARGS = "-p";

    /**
     * Starts the web server on the port specified as command line argument or with default port if none specified
     *
     * @param args
     */
    public static void main(String args[]) {
        try {
            WebServer webServer = new WebServer();
            webServer.start(getPort(args), NO_THREADS, QUEUE_CAPACITY);
        } catch (Exception e) {
            LOGGER.info("Error starting synchronous web server. Cause: " + e.getMessage());
        }
    }

    /**
     * Parses the command line arguments to retrieve the port for starting the web server.
     * If none specified uses @see DEFAULT_PORT
     *
     * @param args
     * @return
     */
    public static int getPort(String args[]) {
        int port = DEFAULT_PORT;
        if (args.length == 2 && PORT_SPECIFIER_ARGS.equals(args[0])) {
            try {
                port = Integer.parseInt(args[1]);
                if (port < 0 || port > 0xFFFF) {
                    LOGGER.info("Invalid port specified. The range of valid port values is between " +
                            "0 and 65535, inclusive. Now using default port " + DEFAULT_PORT);
                    port = DEFAULT_PORT;
                }
            } catch (NumberFormatException e) {
                LOGGER.info("Invalid port specified. Please use this command to start the application: [" +
                        " java -jar syncwebserver.jar -p {portNumber} ]. Now using default port " + DEFAULT_PORT);
            }
        } else {
            LOGGER.info("No port specified. Using default port " + DEFAULT_PORT);
        }
        return port;
    }
}
