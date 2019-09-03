package com.momo.moviecatalogapi.model;

import com.google.gson.annotations.SerializedName;

public class TvShow extends BaseModel<DataTvShow> {
    @SerializedName("total_results")
    private int total_results;

    public TvShow(){

    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
