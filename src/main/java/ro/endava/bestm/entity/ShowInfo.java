package ro.endava.bestm.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/24/14
 */public class ShowInfo {


    @JsonProperty("airtime")
    String airtime;

    @JsonProperty("showid")
    String showId;

    @JsonProperty("status")
    String status;

    @JsonProperty("runtime")
    String runtime;

    @JsonProperty("airday")
    String airDay;

    @JsonProperty("origin_country")
    String originCountry;

    @JsonProperty("timezone")
    String timezone;

    @JsonProperty("startdate")
    String startDate;

    @JsonProperty("seasons")
    String seasons;

    @JsonProperty("showname")
    String showName;

    @JsonProperty("classification")
    String classification;

    @JsonProperty("started")
    String started;

    @JsonProperty("showlink")
    String showLink;


    @JsonProperty("akas")
    Akas akas;

    @JsonProperty("network")
    private Network network;

    public ShowInfo() {
    }

    public String getAirtime() {
        return airtime;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getAirDay() {
        return airDay;
    }

    public void setAirDay(String airDay) {
        this.airDay = airDay;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSeasons() {
        return seasons;
    }

    public void setSeasons(String seasons) {
        this.seasons = seasons;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getShowLink() {
        return showLink;
    }

    public void setShowLink(String showLink) {
        this.showLink = showLink;
    }

    public Akas getAkas() {
        return akas;
    }

    public void setAkas(Akas akas) {
        this.akas = akas;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
}
