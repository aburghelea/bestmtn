package com.endava.service;

import com.google.common.base.Ticker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class TickerService extends Ticker {

    @Autowired
    private TimeService timeService;

    @Override
    public long read() {
        return TimeUnit.MILLISECONDS.toNanos(timeService.currentTimeMillis());
    }

}
