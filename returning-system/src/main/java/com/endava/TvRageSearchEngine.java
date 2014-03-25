package com.endava;

import com.endava.springboot.TvRageApplication;
import com.mangofactory.swagger.configuration.DocumentationConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */
@ComponentScan(basePackages = {"com.endava"})
@EnableAutoConfiguration
public class TvRageSearchEngine {

    public static void main(String[] args) {
        TvRageApplication.run(new Object[]{TvRageSearchEngine.class, DocumentationConfig.class}, args);
    }
}
