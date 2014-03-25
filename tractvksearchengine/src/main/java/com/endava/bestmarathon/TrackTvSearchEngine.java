package com.endava.bestmarathon;

import com.endava.bestmarathon.springboot.TrackTvApp;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by cosmin on 3/25/14.
 */
@ComponentScan(basePackages = {"com.endava.bestmarathon"})
@EnableAutoConfiguration
public class TrackTvSearchEngine {

    public static void main(String args[]){
        TrackTvApp.run(TrackTvSearchEngine.class, args);
    }
}
