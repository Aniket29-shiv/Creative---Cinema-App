package com.oxoo.spagreen.network.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oxoo.spagreen.models.single_details.Country;
import com.oxoo.spagreen.models.single_details.Genre;
import com.oxoo.spagreen.network.model.TvCategory;

import java.util.List;

public class Configuration {
    private int id;
    @SerializedName("app_config")
    @Expose
    private AppConfig appConfig;
    @SerializedName("ads_config")
    @Expose
    private AdsConfig adsConfig;
    @SerializedName("payment_config")
    @Expose
    private PaymentConfig paymentConfig;
    @SerializedName("genre")
    @Expose
    private List<Genre> genre = null;
    @SerializedName("country")
    @Expose
    private List<Country> country = null;
    @SerializedName("tv_category")
    @Expose
    private List<TvCategory> tvCategory = null;
    @SerializedName("apk_version_info")
    @Expose
    private ApkUpdateInfo apkUpdateInfo;

    public Configuration() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public AdsConfig getAdsConfig() {
        return adsConfig;
    }

    public void setAdsConfig(AdsConfig adsConfig) {
        this.adsConfig = adsConfig;
    }

    public PaymentConfig getPaymentConfig() {
        return paymentConfig;
    }

    public void setPaymentConfig(PaymentConfig paymentConfig) {
        this.paymentConfig = paymentConfig;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public List<TvCategory> getTvCategory() {
        return tvCategory;
    }

    public void setTvCategory(List<TvCategory> tvCategory) {
        this.tvCategory = tvCategory;
    }

    public ApkUpdateInfo getApkUpdateInfo() {
        return apkUpdateInfo;
    }

    public void setApkUpdateInfo(ApkUpdateInfo apkUpdateInfo) {
        this.apkUpdateInfo = apkUpdateInfo;
    }
}
