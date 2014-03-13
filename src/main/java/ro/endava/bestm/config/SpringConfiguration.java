package ro.endava.bestm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */
@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
@PropertySource("classpath:application.properties")
public class SpringConfiguration {

    @Value("${taskexecutor.corepoolsize}")
    private final int corePoolSize = 5;
    @Value("${taskexecutor.maxpoolsize}")
    private final int maxPoolSize = 10;
    @Value("${taskexecutor.shutdownwait}")
    private final boolean waitForJobsToCompleteOnShutdown = true;


    Logger logger = LoggerFactory.getLogger(SpringConfiguration.class);

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(waitForJobsToCompleteOnShutdown);

        return taskExecutor;
    }
}
