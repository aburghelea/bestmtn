package com.endava.bestm.crawler;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/17/14
 */
public class TraktTvSeriesDTO {
    String title;
    String year;
    String tvdb_id;
    String tvrage_id;
    String imdb_id;
    Integer plays;
    Boolean in_colection;
    String url;

    public TraktTvSeriesDTO() {
    }

    @Override
    public String toString() {
        return "TraktTvSeriesDTO{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", tvdb_id='" + tvdb_id + '\'' +
                ", tvrage_id='" + tvrage_id + '\'' +
                ", imdb_id='" + imdb_id + '\'' +
                ", plays=" + plays +
                ", in_colection=" + in_colection +
                ", url='" + url + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTvdb_id() {
        return tvdb_id;
    }

    public void setTvdb_id(String tvdb_id) {
        this.tvdb_id = tvdb_id;
    }

    public String getTvrage_id() {
        return tvrage_id;
    }

    public void setTvrage_id(String tvrage_id) {
        this.tvrage_id = tvrage_id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public Integer getPlays() {
        return plays;
    }

    public void setPlays(Integer plays) {
        this.plays = plays;
    }

    public Boolean getIn_colection() {
        return in_colection;
    }

    public void setIn_colection(Boolean in_colection) {
        this.in_colection = in_colection;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
