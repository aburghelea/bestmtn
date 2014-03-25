package ro.endava.bestm.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ro.endava.bestm.datacontainer.DataContainer;
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
    private DataContainer dataContainer;

    public QueryRunner(String query, String callback, DataContainer dataContainer) {
        this.query = query;
        this.callback = callback;
        this.dataContainer = dataContainer;
    }

    @Override
    public void run() {
        logger.info("Running task " + query + " " + callback);

        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.postForObject(callback, dataContainer.get(query),String.class);
        logger.info(response);
    }
}
