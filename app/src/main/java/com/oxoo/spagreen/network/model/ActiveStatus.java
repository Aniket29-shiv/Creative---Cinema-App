package com.oxoo.spagreen.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActiveStatus {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("package_title")
    @Expose
    private String packageTitle;
    @SerializedName("expire_date")
    @Expose
    private String expireDate;

    private long expireTime;

    public ActiveStatus() {
    }

    public String getStatus() {
        return status;
    }

    public String getPackageTitle() {
        return packageTitle;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPackageTitle(String packageTitle) {
        this.packageTitle = packageTitle;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
