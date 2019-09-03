package com.momo.moviecatalogapi.service;

import com.momo.moviecatalogapi.model.Movie;
import com.momo.moviecatalogapi.model.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;


public interface API {

    @GET(Path.API_MOVIE)
    Call<Movie> Movies();

    @GET(Path.TV_SHOW)
    Call<TvShow> TvShows();
}
