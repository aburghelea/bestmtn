package ro.endava.bestmarathon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by cosmin on 3/25/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackTvEntryActor {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
