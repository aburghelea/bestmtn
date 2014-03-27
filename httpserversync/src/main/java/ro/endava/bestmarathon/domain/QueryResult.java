package ro.endava.bestmarathon.domain;

import java.util.Set;

/**
 * Created by cosmin on 3/27/14.
 * Result to be serialized as the response for a query
 */
public class QueryResult {

    private int noEntries;
    private Set<TrackTVEntry> content;

    public QueryResult() {

    }

    public QueryResult(Set<TrackTVEntry> content, int noEntries) {
        this.content = content;
        this.noEntries = noEntries;
    }

    public Set<TrackTVEntry> getContent() {
        return content;
    }

    public void setContent(Set<TrackTVEntry> content) {
        this.content = content;
    }

    public int getNoEntries() {
        return noEntries;
    }

    public void setNoEntries(int noEntries) {
        this.noEntries = noEntries;
    }
}
