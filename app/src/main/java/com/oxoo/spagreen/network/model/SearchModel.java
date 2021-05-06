package com.oxoo.spagreen.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchModel implements Serializable {


    @SerializedName("tvseries")
    @Expose
    private List<CommonModel> tvseries = null;

    @SerializedName("movie")
    @Expose
    private List<CommonModel> movie = null;

    @SerializedName("tv_channels")
    @Expose
    private List<TvModel> tvChannels = null;

    public List<CommonModel> getMovie() {
        return movie;
    }

    public void setMovie(List<CommonModel> movie) {
        this.movie = movie;
    }

    public List<CommonModel> getTvseries() {
        return tvseries;
    }

    public void setTvseries(List<CommonModel> tvseries) {
        this.tvseries = tvseries;
    }

    public List<TvModel> getTvChannels() {
        return tvChannels;
    }

    public void setTvChannels(List<TvModel> tvChannels) {
        this.tvChannels = tvChannels;
    }

    @Override
    public String toString() {
        return "SearchModel{" +
                "movie=" + movie +
                ", tvseries=" + tvseries +
                ", tvChannels=" + tvChannels +
                '}';
    }
}
