package com.endava.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/25/14
 */
public class TvRageApplication extends SpringApplication {
    @Override
    protected void printBanner() {
        TvrageBanner banner = new TvrageBanner();
        banner.print(System.out);
    }

    public TvRageApplication(Object... sources) {
        super(sources);
    }

    public static ConfigurableApplicationContext run(Object[] sources, String[] args) {
        return new TvRageApplication(sources).run(args);
    }
}
