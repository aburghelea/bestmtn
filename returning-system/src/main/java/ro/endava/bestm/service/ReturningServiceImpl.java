package ro.endava.bestm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import ro.endava.bestm.commons.QueryRunner;
import ro.endava.bestm.datacontainer.DataContainer;import ro.endava.bestm.exception.InvaliCallBackException;
import ro.endava.bestm.exception.RandomServerException;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.ServerException;
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
    public void computeResult(String query, String callback) throws InvaliCallBackException, RandomServerException {
        validateCallback(callback);
        simulateFail();
        taskExecutor.execute(new QueryRunner(query, callback, dataContainer));
    }

    private void simulateFail() throws RandomServerException {
        Random random = new Random();
        if (random.nextInt(100) < failRate) {
            throw new RandomServerException("Your request could not be processed due to unknown causes. Try again or contact an admin");
        }
    }

    private void validateCallback(String callback) throws InvaliCallBackException {
        try {
            URL url = new URL(callback);
        } catch (MalformedURLException e) {
            throw new InvaliCallBackException("Try a valid callback buddy. Don't forget to have it URL Encoded");
        }


    }
}
