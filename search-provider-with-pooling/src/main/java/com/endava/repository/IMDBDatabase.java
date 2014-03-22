package com.endava.repository;

import com.endava.domain.IMDBEntry;

import java.util.List;

/**
 * Ionuț Păduraru
 */
public class IMDBDatabase {

    private List<IMDBEntry> data;

    public IMDBDatabase(List<IMDBEntry> data) {
        this.data = data;
    }

    public List<IMDBEntry> getData() {
        return data;
    }
}
