
package com.oxoo.spagreen.models.single_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subtitle {

    @SerializedName("subtitle_id")
    @Expose
    private String subtitleId;
    @SerializedName("videos_id")
    @Expose
    private String videosId;
    @SerializedName("video_file_id")
    @Expose
    private String videoFileId;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("srclang")
    @Expose
    private String srclang;

    public String getSubtitleId() {
        return subtitleId;
    }

    public void setSubtitleId(String subtitleId) {
        this.subtitleId = subtitleId;
    }

    public String getVideosId() {
        return videosId;
    }

    public void setVideosId(String videosId) {
        this.videosId = videosId;
    }

    public String getVideoFileId() {
        return videoFileId;
    }

    public void setVideoFileId(String videoFileId) {
        this.videoFileId = videoFileId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSrclang() {
        return srclang;
    }

    public void setSrclang(String srclang) {
        this.srclang = srclang;
    }

}
