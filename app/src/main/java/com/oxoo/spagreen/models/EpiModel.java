package com.oxoo.spagreen.models;

import com.oxoo.spagreen.models.single_details.Subtitle;

import java.util.List;

public class EpiModel {
    String seson,epi,streamURL,serverType, imageUrl;
    List<SubtitleModel> subtitleList;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getStreamURL() {
        return streamURL;
    }

    public void setStreamURL(String streamURL) {
        this.streamURL = streamURL;
    }

    public String getSeson() {
        return seson;
    }

    public void setSeson(String seson) {
        this.seson = seson;
    }

    public String getEpi() {
        return epi;
    }

    public void setEpi(String epi) {
        this.epi = epi;
    }

    public List<SubtitleModel> getSubtitleList() {
        return subtitleList;
    }

    public void setSubtitleList(List<SubtitleModel> subtitleList) {
        this.subtitleList = subtitleList;
    }
}
