
package com.oxoo.spagreen.models.single_details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("video_file_id")
    @Expose
    private String videoFileId;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("stream_key")
    @Expose
    private String streamKey;
    @SerializedName("file_type")
    @Expose
    private String fileType;
    @SerializedName("file_url")
    @Expose
    private String fileUrl;
    @SerializedName("subtitle")
    @Expose
    private List<Subtitle> subtitle = null;

    public String getVideoFileId() {
        return videoFileId;
    }

    public void setVideoFileId(String videoFileId) {
        this.videoFileId = videoFileId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public List<Subtitle> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(List<Subtitle> subtitle) {
        this.subtitle = subtitle;
    }

}
