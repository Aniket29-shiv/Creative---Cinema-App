package com.oxoo.spagreen.network.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdsConfig {

    @SerializedName("ads_enable")
    @Expose
    private String adsEnable;
    @SerializedName("mobile_ads_network")
    @Expose
    private String mobileAdsNetwork;
    @SerializedName("admob_app_id")
    @Expose
    private String admobAppId;
    @SerializedName("admob_banner_ads_id")
    @Expose
    private String admobBannerAdsId;
    @SerializedName("admob_interstitial_ads_id")
    @Expose
    private String admobInterstitialAdsId;
    @SerializedName("admob_native_ads_id")
    @Expose
    private String admobNativeAdsId;
    @SerializedName("fan_native_ads_placement_id")
    @Expose
    private String fanNativeAdsPlacementId;
    @SerializedName("fan_banner_ads_placement_id")
    @Expose
    private String fanBannerAdsPlacementId;
    @SerializedName("fan_interstitial_ads_placement_id")
    @Expose
    private String fanInterstitialAdsPlacementId;
    @SerializedName("startapp_app_id")
    @Expose
    private String startappAppId;
    @SerializedName("startapp_developer_id")
    @Expose
    private String startappDeveloperId;

    public AdsConfig() {
    }

    public String getAdsEnable() {
        return adsEnable;
    }

    public void setAdsEnable(String adsEnable) {
        this.adsEnable = adsEnable;
    }

    public String getMobileAdsNetwork() {
        return mobileAdsNetwork;
    }

    public void setMobileAdsNetwork(String mobileAdsNetwork) {
        this.mobileAdsNetwork = mobileAdsNetwork;
    }

    public String getAdmobAppId() {
        return admobAppId;
    }

    public void setAdmobAppId(String admobAppId) {
        this.admobAppId = admobAppId;
    }

    public String getAdmobBannerAdsId() {
        return admobBannerAdsId;
    }

    public void setAdmobBannerAdsId(String admobBannerAdsId) {
        this.admobBannerAdsId = admobBannerAdsId;
    }

    public String getAdmobInterstitialAdsId() {
        return admobInterstitialAdsId;
    }

    public void setAdmobInterstitialAdsId(String admobInterstitialAdsId) {
        this.admobInterstitialAdsId = admobInterstitialAdsId;
    }

    public String getFanNativeAdsPlacementId() {
        return fanNativeAdsPlacementId;
    }

    public void setFanNativeAdsPlacementId(String fanNativeAdsPlacementId) {
        this.fanNativeAdsPlacementId = fanNativeAdsPlacementId;
    }

    public String getFanBannerAdsPlacementId() {
        return fanBannerAdsPlacementId;
    }

    public void setFanBannerAdsPlacementId(String fanBannerAdsPlacementId) {
        this.fanBannerAdsPlacementId = fanBannerAdsPlacementId;
    }

    public String getAdmobNativeAdsId() {
        return admobNativeAdsId;
    }

    public void setAdmobNativeAdsId(String admobNativeAdsId) {
        this.admobNativeAdsId = admobNativeAdsId;
    }

    public String getFanInterstitialAdsPlacementId() {
        return fanInterstitialAdsPlacementId;
    }

    public void setFanInterstitialAdsPlacementId(String fanInterstitialAdsPlacementId) {
        this.fanInterstitialAdsPlacementId = fanInterstitialAdsPlacementId;
    }

    public String getStartappAppId() {
        return startappAppId;
    }

    public void setStartappAppId(String startappAppId) {
        this.startappAppId = startappAppId;
    }

    public String getStartappDeveloperId() {
        return startappDeveloperId;
    }

    public void setStartappDeveloperId(String startappDeveloperId) {
        this.startappDeveloperId = startappDeveloperId;
    }
}
