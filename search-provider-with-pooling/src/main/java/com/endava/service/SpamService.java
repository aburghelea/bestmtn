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

    @Autowired
    private BlackListRepository blackList;
    private Cache<String, String> requestCache1S;
    private Cache<String, String> requestCache10S;
    private Cache<String, String> requestCache60S;
    private List<SpamThreshold> thresholds;

    public SpamService() {
        requestCache1S = CacheBuilder.newBuilder().maximumSize(2000).expireAfterWrite(1, TimeUnit.SECONDS).build();
        requestCache10S = CacheBuilder.newBuilder().maximumSize(2000).expireAfterWrite(10, TimeUnit.SECONDS).build();
        requestCache60S = CacheBuilder.newBuilder().maximumSize(2000).expireAfterWrite(60, TimeUnit.SECONDS).build();
        thresholds = new ArrayList<>();
        thresholds.add(new SpamThreshold(requestCache60S, 500, "Oh dear, we have a senior spamer :(   %d request per %d seconds is really bad..."));
        thresholds.add(new SpamThreshold(requestCache10S, 50, "Oh dear, we have a spamer :(   %d request per %d seconds is bad..."));
        thresholds.add(new SpamThreshold(requestCache1S, 5, "Oh dear, we have a junior spamer :(   %d request per %d second is not good..."));
    }

    public void checkSpam(String remoteHost) throws IllegalStateException {
        String key = "" + System.currentTimeMillis();
        requestCache1S.put(key, remoteHost);
        requestCache10S.put(key, remoteHost);
        requestCache60S.put(key, remoteHost);

        for (SpamThreshold threshold : thresholds) {
            threshold.cache.cleanUp();
            long crtSize = threshold.cache.size();
            if (crtSize > threshold.size) {
                blackList.add(remoteHost, "size_" + threshold.size);
                throw new IllegalStateException(String.format(threshold.msgTemplate, crtSize, threshold.size));
            }
        }
    }

    class SpamThreshold {
        Cache<String, String> cache;
        int size;
        String msgTemplate;

        SpamThreshold(Cache<String, String> cache, int size, String msgTemplate) {
            this.cache = cache;
            this.size = size;
            this.msgTemplate = msgTemplate;
        }
    }

}
