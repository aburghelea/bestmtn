package com.endava.service;

import com.endava.repository.BlackListRepository;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class SpamService {

    private boolean active = true;

    @Autowired
    private BlackListRepository blackList;
    private Cache<String, String> requestCache1S;
    private Cache<String, String> requestCache10S;
    private Cache<String, String> requestCache60S;
    private List<SpamThreshold> thresholds;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public SpamService() {
        requestCache1S = CacheBuilder.newBuilder().maximumSize(2000).expireAfterWrite(1, TimeUnit.SECONDS).build();
        requestCache10S = CacheBuilder.newBuilder().maximumSize(2000).expireAfterWrite(10, TimeUnit.SECONDS).build();
        requestCache60S = CacheBuilder.newBuilder().maximumSize(2000).expireAfterWrite(60, TimeUnit.SECONDS).build();
        thresholds = new ArrayList<>();
        thresholds.add(new SpamThreshold(requestCache60S, 500, "Oh dear, we have a senior spamer :(   %d request per %d seconds is really bad...", true));
        thresholds.add(new SpamThreshold(requestCache10S, 50, "Oh dear, we have a spamer :(   %d request per %d seconds is bad..."));
        thresholds.add(new SpamThreshold(requestCache1S, 5, "Oh dear, we have a junior spamer :(   %d request per %d second is not good..."));
    }

    public void checkSpam(String remoteHost) throws IllegalStateException {
        if (blackList.contains(remoteHost)) {
            throw new IllegalStateException("You again?");
        }
        if (!active) {
            return;
        }
        String key = "" + System.currentTimeMillis();
        requestCache1S.put(key, remoteHost);
        requestCache10S.put(key, remoteHost);
        requestCache60S.put(key, remoteHost);

        for (SpamThreshold threshold : thresholds) {
            threshold.cache.cleanUp();
            long crtSize = threshold.cache.size();
            if (crtSize > threshold.size) {
                if (threshold.blacklist) {
                    blackList.add(remoteHost, "size_" + threshold.size);
                }
                throw new IllegalStateException(String.format(threshold.msgTemplate, crtSize, threshold.size));
            }
        }
    }

    class SpamThreshold {
        Cache<String, String> cache;
        int size;
        String msgTemplate;
        boolean blacklist = false;

        SpamThreshold(Cache<String, String> cache, int size, String msgTemplate) {
            this(cache, size, msgTemplate, false);
        }
        SpamThreshold(Cache<String, String> cache, int size, String msgTemplate, boolean blacklist) {
            this.cache = cache;
            this.size = size;
            this.msgTemplate = msgTemplate;
            this.blacklist = blacklist;
        }
    }

}
