package com.oxoo.spagreen.models.single_details_tv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdditionalMediaSource {
    @SerializedName("live_tv_id")
    @Expose
    private String liveTvId;
    @SerializedName("stream_key")
    @Expose
    private String streamKey;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("url")
    @Expose
    private String url;

    public String getLiveTvId() {
        return liveTvId;
    }

    public void setLiveTvId(String liveTvId) {
        this.liveTvId = liveTvId;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
