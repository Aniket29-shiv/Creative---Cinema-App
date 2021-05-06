
package com.oxoo.spagreen.models.single_details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadLink {

    @SerializedName("download_link_id")
    @Expose
    private String downloadLinkId;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("videos_id")
    @Expose
    private String videosId;
    @SerializedName("resolution")
    @Expose
    private String resolution;
    @SerializedName("file_size")
    @Expose
    private String fileSize;
    @SerializedName("download_url")
    @Expose
    private String downloadUrl;
    @SerializedName("in_app_download")
    @Expose
    private boolean isInAppDownload;

    public String getDownloadLinkId() {
        return downloadLinkId;
    }

    public void setDownloadLinkId(String downloadLinkId) {
        this.downloadLinkId = downloadLinkId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVideosId() {
        return videosId;
    }

    public void setVideosId(String videosId) {
        this.videosId = videosId;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public boolean isInAppDownload() {
        return isInAppDownload;
    }

    public void setInAppDownload(boolean inAppDownload) {
        isInAppDownload = inAppDownload;
    }
}
