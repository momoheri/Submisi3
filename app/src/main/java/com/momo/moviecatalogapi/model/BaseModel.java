package com.momo.moviecatalogapi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseModel<T> {

    @SerializedName("results")
    private List<T> results;

    public BaseModel() {

    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

}
