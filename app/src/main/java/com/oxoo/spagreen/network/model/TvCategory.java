package com.oxoo.spagreen.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvCategory {
    @SerializedName("live_tv_category_id")
    @Expose
    private String liveTvCategoryId;
    @SerializedName("live_tv_category")
    @Expose
    private String liveTvCategory;
    @SerializedName("live_tv_category_desc")
    @Expose
    private String liveTvCategoryDesc;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("slug")
    @Expose
    private String slug;

    public String getLiveTvCategoryId() {
        return liveTvCategoryId;
    }

    public void setLiveTvCategoryId(String liveTvCategoryId) {
        this.liveTvCategoryId = liveTvCategoryId;
    }

    public String getLiveTvCategory() {
        return liveTvCategory;
    }

    public void setLiveTvCategory(String liveTvCategory) {
        this.liveTvCategory = liveTvCategory;
    }

    public String getLiveTvCategoryDesc() {
        return liveTvCategoryDesc;
    }

    public void setLiveTvCategoryDesc(String liveTvCategoryDesc) {
        this.liveTvCategoryDesc = liveTvCategoryDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
