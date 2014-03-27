package ro.endava.bestmarathon.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.endava.bestmarathon.domain.TrackTVEntry;

import java.io.*;
import java.util.*;

import static ro.endava.bestmarathon.utils.Utils.checkCollection;
import static ro.endava.bestmarathon.utils.Utils.split;

/**
 * Created by cosmin on 3/24/14.
 * Singleton class that holds the virtual database with tv series
 */
public class VirtualDB {

    private final static Logger LOGGER = LoggerFactory.getLogger(VirtualDB.class);

    private static class VirtualDBHolder {
        public static VirtualDB instance = new VirtualDB();
    }

    private List<TrackTVEntry> data;
    private List<Set<String>> index;

    private VirtualDB() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream jsonStream = VirtualDB.class.getResourceAsStream("/tracktv.json");
            if (jsonStream != null) {
                try {
                    data = objectMapper.readValue(jsonStream, new TypeReference<List<TrackTVEntry>>() {
                    });
                } finally {
                    jsonStream.close();
                }
            }

            index = new ArrayList<>(data.size());
            for (TrackTVEntry trackTVEntry : data) {
                LOGGER.info("Indexing tv show [" + trackTVEntry.getTitle() + "]");
                Set<String> words = new HashSet<>();
                words.addAll(split(trackTVEntry.getTitle()));
                words.addAll(split(String.valueOf(trackTVEntry.getYear())));
                words.addAll(split(trackTVEntry.getOverview()));
                words.addAll(split(trackTVEntry.getCountry()));
                words.addAll(split(trackTVEntry.getStatus()));
                words.addAll(split(trackTVEntry.getNetwork()));
                words.addAll(split(trackTVEntry.getAirDay()));
                words.addAll(split(trackTVEntry.getAirTime()));
                words.addAll(split(trackTVEntry.getImdbId()));
                words.addAll(split(trackTVEntry.getTvRageId()));
                words.addAll(checkCollection(trackTVEntry.getGenres()));
                index.add(words);
            }
        } catch (IOException e) {
            LOGGER.error("Oups..Our database is not properly built. Cause: " + e.getMessage());
        }
    }

    public static VirtualDB getInstance() {
        return VirtualDBHolder.instance;
    }

    public List<TrackTVEntry> getData() {
        return data;
    }

    public List<Set<String>> getIndex() {
        return index;
    }
}
