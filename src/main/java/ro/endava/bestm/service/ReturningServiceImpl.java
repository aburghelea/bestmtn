package ro.endava.bestm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import ro.endava.bestm.commons.QueryRunner;

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

    @Override
    public void computeResult(String query, String callback) {
        taskExecutor.execute(new QueryRunner(query, callback));
    }
}
