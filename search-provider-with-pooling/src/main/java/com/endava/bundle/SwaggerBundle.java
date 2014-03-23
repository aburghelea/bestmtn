package com.endava.bundle;

import com.endava.configuration.IMDBSearchEngineConfig;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;
import io.dropwizard.Bundle;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * @author Ionuț Păduraru
 */
public class SwaggerBundle implements ConfiguredBundle<IMDBSearchEngineConfig> {


    public SwaggerBundle() {
    }

    @Override
    public void run(IMDBSearchEngineConfig configuration, Environment environment) {
        ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());
        SwaggerConfig swaggerConfig = ConfigFactory.config();
        swaggerConfig.setApiVersion("1.0");
        swaggerConfig.setBasePath(configuration.getBasePath());
        environment.jersey().register(new ApiListingResourceJSON());
        environment.jersey().register(new ApiDeclarationProvider());
        environment.jersey().register(new ResourceListingProvider());
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
}
