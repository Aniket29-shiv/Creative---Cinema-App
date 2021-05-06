package com.oxoo.spagreen.models.home_content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PopularStars {
    @SerializedName("star_id")
    @Expose
    private String startId;

    @SerializedName("star_name")
    @Expose
    private String starName;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
