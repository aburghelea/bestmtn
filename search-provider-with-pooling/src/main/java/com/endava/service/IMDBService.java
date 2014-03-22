package com.endava.service;

import com.endava.domain.IMDBEntry;
import com.endava.repository.IMDBDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Ionuț Păduraru
 */
@Service
public class IMDBService {

    @Autowired
    private IMDBDatabase database;

    public List<IMDBEntry> find(String query) {
        List<IMDBEntry> result = new ArrayList<>();
        if (!StringUtils.hasText(query)) {
            return result;
        }
        for (IMDBEntry imdbEntry : database.getData()) {
            if (match(imdbEntry, query)) {
                result.add(imdbEntry);
            }
        }
        return result;
    }

    private boolean match(IMDBEntry imdbEntry, String query) {
        if (imdbEntry.getTitle().toLowerCase().contains(query.toLowerCase())) {
            return true;
        }
        return false;
    }

}
