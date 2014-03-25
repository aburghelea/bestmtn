package com.endava.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/24/14
 */
public class Aka {


    @JsonProperty("content")
    private String content;

    @JsonProperty("country")
    private String county;

    @JsonProperty("attr")
    private String attribute;

    public Aka() {
    }


    public Aka(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
