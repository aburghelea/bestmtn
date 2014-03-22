package com.endava.health;

import com.codahale.metrics.health.HealthCheck;
import com.endava.repository.IMDBDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Ionuț Păduraru
 */
@Component
public class DBHealthCheck extends HealthCheck {

    @Autowired
    private IMDBDatabase database;

    protected DBHealthCheck() {
        super();
    }

    @Override
    protected Result check() throws Exception {
        if (database.getData() != null) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Hm, where did the data go?");
        }
    }

}
