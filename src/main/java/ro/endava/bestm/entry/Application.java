package ro.endava.bestm.entry;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import ro.endava.bestm.config.SpringConfiguration;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/13/14
 */
@ComponentScan(basePackages = {"ro.endava.bestm"})
@EnableAutoConfiguration
public class Application{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
