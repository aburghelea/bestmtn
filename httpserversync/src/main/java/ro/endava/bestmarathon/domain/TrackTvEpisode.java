package ro.endava.bestmarathon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by cosmin on 3/25/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackTvEpisode {

    private long plays;
    private int season;
    private int number;
    private String title;
    private String url;

    public long getPlays() {
        return plays;
    }

    public void setPlays(long plays) {
        this.plays = plays;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
