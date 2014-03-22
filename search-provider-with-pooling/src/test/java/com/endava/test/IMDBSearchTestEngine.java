package com.endava.test;

import com.endava.IMDBSearchEngine;
import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Ionuț Păduraru
 */
public class IMDBSearchTestEngine extends IMDBSearchEngine {
    private AnnotationConfigApplicationContext context;
    @Override
    public ConfigurableApplicationContext applicationContext() throws BeansException {
        if (context == null) {
            context = new AnnotationConfigApplicationContext();
            context.scan("com.endava.health");
            context.scan("com.endava.repository");
            context.scan("com.endava.service");
            context.scan("com.endava.task");
            context.scan("com.endava.resource");
            context.scan("com.endava.test");
        }
        return context;
    }
}
