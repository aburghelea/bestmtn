package com.endava;

import com.endava.bundle.CrossOriginBundle;
import com.endava.bundle.SwaggerBundle;
import com.endava.configuration.IMDBSearchEngineConfig;
import com.endava.service.TimeServiceImpl;
import com.github.nhuray.dropwizard.spring.SpringBundle;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Ionuț Păduraru
 */
public class IMDBSearchEngine extends Application<IMDBSearchEngineConfig> {

    public static void main(final String[] args) throws Exception {
        new IMDBSearchEngine().run(args);
    }

    @Override
    public void initialize(final Bootstrap<IMDBSearchEngineConfig> bootstrap) {
        bootstrap.addBundle(new SpringBundle<>(applicationContext(), true, true, true));
        bootstrap.addBundle(new SwaggerBundle());
        bootstrap.addBundle(new CrossOriginBundle());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/docs", "index.html"));
    }

    @Override
    public void run(IMDBSearchEngineConfig configuration, Environment environment) throws Exception {
    }

    protected ConfigurableApplicationContext applicationContext() throws BeansException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.endava.health");
        context.scan("com.endava.repository");
        context.scan("com.endava.service");
        context.scan("com.endava.task");
        context.scan("com.endava.spring");
        context.scan("com.endava.resource");
        return context;
    }

}
