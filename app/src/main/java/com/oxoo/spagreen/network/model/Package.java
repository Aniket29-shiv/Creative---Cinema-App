package com.oxoo.spagreen.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Package implements Serializable {

    @SerializedName("plan_id")
    @Expose
    private String planId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("screens")
    @Expose
    private String screens;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private String status;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getScreens() {
        return screens;
    }

    public void setScreens(String screens) {
        this.screens = screens;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Package{" +
                "planId='" + planId + '\'' +
                ", name='" + name + '\'' +
                ", day='" + day + '\'' +
                ", screens='" + screens + '\'' +
                ", price='" + price + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
