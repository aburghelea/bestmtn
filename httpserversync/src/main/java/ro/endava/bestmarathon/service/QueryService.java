package ro.endava.bestmarathon.service;

import ro.endava.bestmarathon.db.VirtualDB;
import ro.endava.bestmarathon.domain.TrackTVEntry;
import ro.endava.bestmarathon.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cosmin on 3/25/14.
 */
public class QueryService {

    public Set<TrackTVEntry> findByQuery(String query) throws IOException {
        Set<TrackTVEntry> result = new HashSet<>();

        VirtualDB virtualDB = VirtualDB.getInstance();
        List<TrackTVEntry> data = virtualDB.getData();
        List<Set<String>> index = virtualDB.getIndex();
        if ("**".equals(query)) {
            result.addAll(data);
        } else {
            Set<String> tokens = Utils.split(query);
            for (String token : tokens) {
                for (int i = 0; i < index.size(); i++) {
                    if (index.get(i).contains(token)) {
                        result.add(data.get(i));
                    }
                }
            }
        }
        return result;
    }
}
