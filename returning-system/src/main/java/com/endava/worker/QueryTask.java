package com.endava.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import com.endava.datacontainer.DataContainer;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */

public class QueryTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(QueryTask.class);
    private String query;
    private String callback;
    private DataContainer dataContainer;

    public QueryTask(String query, String callback, DataContainer dataContainer) {
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
