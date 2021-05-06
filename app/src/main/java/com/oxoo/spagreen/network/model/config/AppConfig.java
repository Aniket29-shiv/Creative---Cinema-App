package com.oxoo.spagreen.network.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppConfig {
    @SerializedName("menu")
    @Expose
    private String menu;
    @SerializedName("program_guide_enable")
    @Expose
    private Boolean programGuideEnable;
    @SerializedName("mandatory_login")
    @Expose
    private Boolean mandatoryLogin;
    @SerializedName("genre_visible")
    @Expose
    private Boolean genreVisible;
    @SerializedName("country_visible")
    @Expose
    private Boolean countryVisible;

    public AppConfig() {
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Boolean getProgramGuideEnable() {
        return programGuideEnable;
    }

    public void setProgramGuideEnable(Boolean programGuideEnable) {
        this.programGuideEnable = programGuideEnable;
    }

    public Boolean getMandatoryLogin() {
        return mandatoryLogin;
    }

    public void setMandatoryLogin(Boolean mandatoryLogin) {
        this.mandatoryLogin = mandatoryLogin;
    }

    public Boolean getGenreVisible() {
        return genreVisible;
    }

    public void setGenreVisible(Boolean genreVisible) {
        this.genreVisible = genreVisible;
    }

    public Boolean getCountryVisible() {
        return countryVisible;
    }

    public void setCountryVisible(Boolean countryVisible) {
        this.countryVisible = countryVisible;
    }
}
