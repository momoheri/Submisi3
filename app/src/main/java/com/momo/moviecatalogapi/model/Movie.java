package com.momo.moviecatalogapi.model;

import com.google.gson.annotations.SerializedName;

public class Movie extends BaseModel<DataMovie> {
    @SerializedName("total_results")
    private int totalResult;


    public Movie() {
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

}
