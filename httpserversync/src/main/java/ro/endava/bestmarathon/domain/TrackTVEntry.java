package ro.endava.bestmarathon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by cosmin on 3/24/14.
 * Entity for a tvseries entry
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackTVEntry {

    private String title;
    private long year;
    private String url;
    private String country;
    private String overview;
    private String status;
    private String network;
    @JsonProperty(value = "air_day")
    private String airDay;
    @JsonProperty(value = "air_time")
    private String airTime;
    @JsonProperty(value = "imdb_id")
    private String imdbId;
    @JsonProperty(value = "tvrage_id")
    private String tvRageId;
    private String poster;
    @JsonProperty(value = "top_episodes")
    private List<TrackTvEpisode> topEpisodes;
    private List<String> genres;
    private TrackTvEntryPeople people;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getAirDay() {
        return airDay;
    }

    public void setAirDay(String airDay) {
        this.airDay = airDay;
    }

    public String getAirTime() {
        return airTime;
    }

    public void setAirTime(String airTime) {
        this.airTime = airTime;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTvRageId() {
        return tvRageId;
    }

    public void setTvRageId(String tvRageId) {
        this.tvRageId = tvRageId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public List<TrackTvEpisode> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(List<TrackTvEpisode> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public TrackTvEntryPeople getPeople() {
        return people;
    }

    public void setPeople(TrackTvEntryPeople people) {
        this.people = people;
    }
}
