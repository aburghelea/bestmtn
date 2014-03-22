package com.endava.service;

import org.springframework.stereotype.Component;

/**
 * Ionuț Păduraru
 */
public class TimeServiceImpl implements TimeService {
    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
