package ro.endava.bestm.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import ro.endava.bestm.dto.SimpleResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */
public class QueryRunner implements Runnable {

    private Logger logger = LoggerFactory.getLogger(QueryRunner.class);
    private String query;
    private String callback;

    public QueryRunner(String query, String callback) {
        this.query = query;
        this.callback = callback;
    }

    @Override
    public void run() {
        logger.info("Running task " + query + " " + callback);

        RestTemplate restTemplate = new RestTemplate();
        SimpleResponse sr = restTemplate.getForObject("http://localhost:8080/callback?query=123", SimpleResponse.class);

        logger.info("Received " + sr);
        logger.info("finished");
    }
}
