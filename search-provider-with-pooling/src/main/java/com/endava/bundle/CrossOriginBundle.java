package com.endava.bundle;

import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Ionuț Păduraru
 */
public class CrossOriginBundle implements Bundle {

    @Override
    public void run(Environment environment){
        FilterHolder filterHolder = new FilterHolder(new CrossOriginFilter());
        filterHolder.getInitParameters().put(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        environment.getApplicationContext().getServletHandler().addFilterWithMapping(filterHolder, "/*", EnumSet.allOf(DispatcherType.class));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }
}
