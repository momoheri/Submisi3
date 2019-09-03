package com.momo.moviecatalogapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DataTvShow implements Parcelable {


    @SerializedName("original_name")
    private String title;
    @SerializedName("first_air_date")
    private String first_air_date;
    @SerializedName("id")
    private int id;
    @SerializedName("vote_average")
    private Double vote_average;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String poster_path;




    public DataTvShow(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    protected DataTvShow(Parcel in) {
        this.title = in.readString();
        this.first_air_date = in.readString();
        this.poster_path = in.readString();
        this.overview = in.readString();
        this.vote_average = in.readDouble();
    }

    public static final Creator<DataTvShow> CREATOR = new Creator<DataTvShow>() {
        @Override
        public DataTvShow createFromParcel(Parcel in) {
            return new DataTvShow(in);
        }

        @Override
        public DataTvShow[] newArray(int size) {
            return new DataTvShow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.first_air_date);
        dest.writeString(this.poster_path);
        dest.writeString(this.overview);
        dest.writeDouble(this.vote_average);
    }
}
