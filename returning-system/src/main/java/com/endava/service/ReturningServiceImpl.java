package com.endava.service;

import com.endava.exception.InvalidCallBackException;
import com.endava.worker.QueryTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.endava.datacontainer.DataContainer;
import com.endava.exception.RandomServerException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */

@Service
public class ReturningServiceImpl implements ReturningService {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private DataContainer dataContainer;

    @Value("${system.failrate}")
    private int failRate;

    @Override
    public void computeResult(String query, String callback) throws InvalidCallBackException, RandomServerException {
        validateCallback(callback);
        simulateFail();
        taskExecutor.execute(new QueryTask(query, callback, dataContainer));
    }

    private void simulateFail() throws RandomServerException {
        Random random = new Random();
        if (random.nextInt(100) < failRate) {
            throw new RandomServerException("Your request could not be processed due to unknown causes. Try again or contact an admin");
        }
    }

    private void validateCallback(String callback) throws InvalidCallBackException {
        try {
            URL url = new URL(callback);
        } catch (MalformedURLException e) {
            throw new InvalidCallBackException("Try a valid callback buddy. Don't forget to have it URL Encoded");
        }


    }
}
