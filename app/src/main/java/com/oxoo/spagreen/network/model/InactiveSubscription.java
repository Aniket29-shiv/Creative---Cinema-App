package com.oxoo.spagreen.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InactiveSubscription {

    @SerializedName("subscription_id")
    @Expose
    private String subscriptionId;
    @SerializedName("plan_id")
    @Expose
    private String planId;
    @SerializedName("plan_title")
    @Expose
    private String planTitle;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("price_amount")
    @Expose
    private String priceAmount;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("expire_date")
    @Expose
    private String expireDate;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_info")
    @Expose
    private String paymentInfo;
    @SerializedName("payment_timestamp")
    @Expose
    private String paymentTimestamp;
    @SerializedName("status")
    @Expose
    private String status;

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(String priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getPaymentTimestamp() {
        return paymentTimestamp;
    }

    public void setPaymentTimestamp(String paymentTimestamp) {
        this.paymentTimestamp = paymentTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InactiveSubscription{" +
                "subscriptionId='" + subscriptionId + '\'' +
                ", planId='" + planId + '\'' +
                ", planTitle='" + planTitle + '\'' +
                ", userId='" + userId + '\'' +
                ", priceAmount='" + priceAmount + '\'' +
                ", paidAmount='" + paidAmount + '\'' +
                ", startDate='" + startDate + '\'' +
                ", expireDate='" + expireDate + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentInfo='" + paymentInfo + '\'' +
                ", paymentTimestamp='" + paymentTimestamp + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
