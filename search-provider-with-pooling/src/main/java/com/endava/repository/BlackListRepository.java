package com.endava.repository;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class BlackListRepository {

    private Map<String, String> entries = Collections.synchronizedMap(new HashMap<String, String>());


    public void add(String key, String reason) {
        entries.put(key, reason);
    }

    public boolean contains(String key) {
        return entries.containsKey(key);
    }

    public void purge() {
        System.out.println("Purge blacklist request received");
        entries.clear();
    }

    public int size() {
        return entries.size();
    }
}
