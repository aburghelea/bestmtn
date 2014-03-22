package com.endava.repository;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class BlackListRepository {

    private Set<String> entries = Collections.synchronizedSet(new HashSet<String>());


    public Set<String> getEntries() {
        return entries;
    }

    public void add(String key, String reason) {
        // TODO
    }
    public void purge() {
        System.out.println("Purge blacklist request received");
        entries.clear();
    }

}
