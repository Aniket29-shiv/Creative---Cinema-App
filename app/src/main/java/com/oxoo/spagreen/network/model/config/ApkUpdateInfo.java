package com.oxoo.spagreen.network.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApkUpdateInfo {
    @SerializedName("version_code")
    @Expose
    private String versionCode;
    @SerializedName("version_name")
    @Expose
    private String versionName;
    @SerializedName("whats_new")
    @Expose
    private String whatsNew;
    @SerializedName("apk_url")
    @Expose
    private String apkUrl;
    @SerializedName("is_skipable")
    @Expose
    private boolean isSkipable;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getWhatsNew() {
        return whatsNew;
    }

    public void setWhatsNew(String whatsNew) {
        this.whatsNew = whatsNew;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public boolean isSkipable() {
        return isSkipable;
    }

    public void setSkipable(boolean skipable) {
        isSkipable = skipable;
    }
}
