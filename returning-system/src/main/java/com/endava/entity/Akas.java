package com.endava.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * BEST Engineering Marathon 2014
 * Endava Federated Search, Returning(Callback) System
 *
 * @author <a href="mailto:alexandru.burghelea@endava.com">Alexandru BURGHELEA</a>
 * @since 3/24/14
 */public class Akas {


    @JsonProperty("aka")
    private List<Aka> aka;

    public Akas() {
        aka = new ArrayList<Aka>();
    }


    public List<Aka> getAka() {
        return aka;
    }

    public void setAka(List<Aka> aka) {
        this.aka = aka;
    }
}
