package com.oxoo.spagreen.models.single_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Season {

    @SerializedName("seasons_id")
    @Expose
    private String seasonsId;
    @SerializedName("seasons_name")
    @Expose
    private String seasonsName;
    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;
    @SerializedName("enable_download")
    @Expose
    private String enableDownload;
    @SerializedName("download_links")
    @Expose
    private List<DownloadLink> downloadLinks;

    public String getSeasonsId() {
        return seasonsId;
    }

    public void setSeasonsId(String seasonsId) {
        this.seasonsId = seasonsId;
    }

    public String getSeasonsName() {
        return seasonsName;
    }

    public void setSeasonsName(String seasonsName) {
        this.seasonsName = seasonsName;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getEnableDownload() {
        return enableDownload;
    }

    public void setEnableDownload(String enableDownload) {
        this.enableDownload = enableDownload;
    }

    public List<DownloadLink> getDownloadLinks() {
        return downloadLinks;
    }

    public void setDownloadLinks(List<DownloadLink> downloadLinks) {
        this.downloadLinks = downloadLinks;
    }
}
