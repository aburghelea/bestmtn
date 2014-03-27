package ro.endava.bestmarathon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by cosmin on 3/25/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackTvEntryPeople {

    private List<TrackTvEntryActor> actors;

    public List<TrackTvEntryActor> getActors() {
        return actors;
    }

    public void setActors(List<TrackTvEntryActor> actors) {
        this.actors = actors;
    }
}
