package com.endava.bestmarathon.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by cosmin on 3/25/14.
 */
public class TrackTvApp extends SpringApplication {

    public TrackTvApp(Object... sources){
        super(sources);
    }
    public static ConfigurableApplicationContext run(Object[] sources, String[] args) {
        return new TrackTvApp(sources).run(args);
    }
}
