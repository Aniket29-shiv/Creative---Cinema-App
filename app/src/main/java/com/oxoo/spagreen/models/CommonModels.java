package com.oxoo.spagreen.models;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class CommonModels {

    public String id;
    public int image;
    public String imageUrl;
    public Drawable imageDrw;
    public String title;
    public String quality;
    public String brief;
    public String stremURL;
    public String videoType;
    public String releaseDate;
    public String serverType;
    public List<EpiModel> listEpi=new ArrayList<>();
    public String subtitleURL;
    public List<SubtitleModel> listSub = new ArrayList<>();
    public String fileSize;
    public String resulation;
    public String isPaid;
    public String price;
    public boolean isInAppDownload;

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getResulation() {
        return resulation;
    }

    public void setResulation(String resulation) {
        this.resulation = resulation;
    }

    public List<SubtitleModel> getListSub() {
        return listSub;
    }

    public void setListSub(List<SubtitleModel> listSub) {
        this.listSub = listSub;
    }

    //-----tv item------------
    String tvName,posterUrl;
    //ArrayList<EpiModel> epiModels;


    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSubtitleURL() {
        return subtitleURL;
    }

    public void setSubtitleURL(String subtitleURL) {
        this.subtitleURL = subtitleURL;
    }

    public List<EpiModel> getListEpi() {
        return listEpi;
    }

    public void setListEpi(List<EpiModel> listEpi) {
        this.listEpi = listEpi;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getStremURL() {
        return stremURL;
    }

    public void setStremURL(String stremURL) {
        this.stremURL = stremURL;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Drawable getImageDrw() {
        return imageDrw;
    }

    public void setImageDrw(Drawable imageDrw) {
        this.imageDrw = imageDrw;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isInAppDownload() {
        return isInAppDownload;
    }

    public void setInAppDownload(boolean inAppDownload) {
        isInAppDownload = inAppDownload;
    }
}
