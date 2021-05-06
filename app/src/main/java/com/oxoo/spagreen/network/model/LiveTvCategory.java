package com.oxoo.spagreen.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LiveTvCategory implements Serializable {

    @SerializedName("live_tv_category_id")
    @Expose
    private String liveTvCategoryId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("channels")
    @Expose
    private List<Channel> channels = null;

    public String getLiveTvCategoryId() {
        return liveTvCategoryId;
    }

    public void setLiveTvCategoryId(String liveTvCategoryId) {
        this.liveTvCategoryId = liveTvCategoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

}
