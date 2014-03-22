package com.endava.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Ionuț Păduraru
 */
public class IMDBSearchEngineConfig extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private String nodeName;
    @Valid
    @NotNull
    @JsonProperty
    private int minDelayMs;
    @Valid
    @NotNull
    @JsonProperty
    private int maxDelayMs;
    @Valid
    @NotNull
    @JsonProperty
    private int failureSeed;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getMinDelayMs() {
        return minDelayMs;
    }

    public void setMinDelayMs(int minDelayMs) {
        this.minDelayMs = minDelayMs;
    }

    public int getMaxDelayMs() {
        return maxDelayMs;
    }

    public void setMaxDelayMs(int maxDelayMs) {
        this.maxDelayMs = maxDelayMs;
    }

    public int getFailureSeed() {
        return failureSeed;
    }

    public void setFailureSeed(int failureSeed) {
        this.failureSeed = failureSeed;
    }
}
