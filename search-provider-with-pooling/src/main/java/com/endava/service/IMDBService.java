package com.endava.service;

import com.endava.domain.IMDBEntry;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Ionuț Păduraru
 */
public class IMDBService {

    private List<IMDBEntry> data;
    private List<Set<String>> index;

    public IMDBService(List<IMDBEntry> data) {
        this.data = data;
        buildIndex();
    }

    private void buildIndex() {
        index = new ArrayList<>(data.size());
        for (IMDBEntry imdbEntry : data) {
            Set<String> words = new HashSet<>();
            words.addAll(split(imdbEntry.getTitle()));
            words.addAll(split(imdbEntry.getActors()));
            words.addAll(split(imdbEntry.getDirector()));
            words.addAll(split(imdbEntry.getGenre()));
            words.addAll(split(imdbEntry.getPlot()));
            index.add(words);
        }
    }

    private Set<String> split (String s) {
        Set<String> result = new HashSet<>();
        if (s == null) {
            return result;
        }
        StringTokenizer tokenizer = new StringTokenizer(s, " ,.;'\"\\/?!:"); // note: not splitting on "-"
        while(tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            if (StringUtils.hasText(word) && word.length() > 2){
                result.add(word.toLowerCase());
            }
        }
        return result;
    }

    public List<IMDBEntry> getData() {
        return data;
    }

    public List<IMDBEntry> find(String query) {
        List<IMDBEntry> result = new ArrayList<>();
        for (String queryItem : split(query)) {
            for(int i = 0; i < index.size(); i++) {
                if (index.get(i).contains(queryItem)) {
                    result.add(data.get(i));
                }
            }
        }
        return result;
    }

}
