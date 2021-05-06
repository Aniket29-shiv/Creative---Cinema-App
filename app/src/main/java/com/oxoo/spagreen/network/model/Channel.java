package com.oxoo.spagreen.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Channel implements Serializable {

    @SerializedName("live_tv_id")
    @Expose
    private String liveTvId;
    @SerializedName("tv_name")
    @Expose
    private String tvName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("stream_from")
    @Expose
    private String streamFrom;
    @SerializedName("stream_label")
    @Expose
    private String streamLabel;
    @SerializedName("stream_url")
    @Expose
    private String streamUrl;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("poster_url")
    @Expose
    private String posterUrl;
    @SerializedName("is_paid")
    @Expose
    private String isPaid;

    private String videoType;

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getLiveTvId() {
        return liveTvId;
    }

    public void setLiveTvId(String liveTvId) {
        this.liveTvId = liveTvId;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStreamFrom() {
        return streamFrom;
    }

    public void setStreamFrom(String streamFrom) {
        this.streamFrom = streamFrom;
    }

    public String getStreamLabel() {
        return streamLabel;
    }

    public void setStreamLabel(String streamLabel) {
        this.streamLabel = streamLabel;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "liveTvId='" + liveTvId + '\'' +
                ", tvName='" + tvName + '\'' +
                ", description='" + description + '\'' +
                ", slug='" + slug + '\'' +
                ", streamFrom='" + streamFrom + '\'' +
                ", streamLabel='" + streamLabel + '\'' +
                ", streamUrl='" + streamUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", videoType='" + videoType + '\'' +
                '}';
    }
}
