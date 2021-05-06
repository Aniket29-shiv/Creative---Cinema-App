package com.oxoo.spagreen.network.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentConfig {
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("exchnage_rate")
    @Expose
    private String exchangeRate;
    @SerializedName("paypal_enable")
    @Expose
    private Boolean paypalEnable;
    @SerializedName("paypal_email")
    @Expose
    private String paypalEmail;
    @SerializedName("paypal_client_id")
    @Expose
    private String paypalClientId;
    @SerializedName("stripe_enable")
    @Expose
    private Boolean stripeEnable;
    @SerializedName("stripe_publishable_key")
    @Expose
    private String stripePublishableKey;
    @SerializedName("stripe_secret_key")
    @Expose
    private String stripeSecretKey;
    @SerializedName("razorpay_enable")
    @Expose
    private Boolean razorpayEnable;
    @SerializedName("razorpay_key_id")
    @Expose
    private String razorpayKeyId;
    @SerializedName("razorpay_key_secret")
    @Expose
    private String razorpayKeySecret;
    @SerializedName("razorpay_inr_exchange_rate")
    @Expose
    private String razorpayExchangeRate;
    @SerializedName("offline_payment_enable")
    @Expose
    private boolean offlinePaymentEnable;
    @SerializedName("offline_payment_title")
    @Expose
    private String offlinePaymentTitle;
    @SerializedName("offline_payment_instruction")
    @Expose
    private String offlinePaymentInstruction;


    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Boolean getPaypalEnable() {
        return paypalEnable;
    }

    public void setPaypalEnable(Boolean paypalEnable) {
        this.paypalEnable = paypalEnable;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public void setPaypalEmail(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    public String getPaypalClientId() {
        return paypalClientId;
    }

    public void setPaypalClientId(String paypalClientId) {
        this.paypalClientId = paypalClientId;
    }

    public Boolean getStripeEnable() {
        return stripeEnable;
    }

    public void setStripeEnable(Boolean stripeEnable) {
        this.stripeEnable = stripeEnable;
    }

    public String getStripePublishableKey() {
        return stripePublishableKey;
    }

    public void setStripePublishableKey(String stripePublishableKey) {
        this.stripePublishableKey = stripePublishableKey;
    }

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }

    public void setStripeSecretKey(String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
    }

    public Boolean getRazorpayEnable() {
        return razorpayEnable;
    }

    public void setRazorpayEnable(Boolean razorpayEnable) {
        this.razorpayEnable = razorpayEnable;
    }

    public String getRazorpayKeyId() {
        return razorpayKeyId;
    }

    public void setRazorpayKeyId(String razorpayKeyId) {
        this.razorpayKeyId = razorpayKeyId;
    }

    public String getRazorpayKeySecret() {
        return razorpayKeySecret;
    }

    public void setRazorpayKeySecret(String razorpayKeySecret) {
        this.razorpayKeySecret = razorpayKeySecret;
    }

    public String getRazorpayExchangeRate() {
        return razorpayExchangeRate;
    }

    public void setRazorpayExchangeRate(String razorpayExchangeRate) {
        this.razorpayExchangeRate = razorpayExchangeRate;
    }

    public boolean isOfflinePaymentEnable() {
        return offlinePaymentEnable;
    }

    public void setOfflinePaymentEnable(boolean offlinePaymentEnable) {
        this.offlinePaymentEnable = offlinePaymentEnable;
    }

    public String getOfflinePaymentTitle() {
        return offlinePaymentTitle;
    }

    public void setOfflinePaymentTitle(String offlinePaymentTitle) {
        this.offlinePaymentTitle = offlinePaymentTitle;
    }

    public String getOfflinePaymentInstruction() {
        return offlinePaymentInstruction;
    }

    public void setOfflinePaymentInstruction(String offlinePaymentInstruction) {
        this.offlinePaymentInstruction = offlinePaymentInstruction;
    }
}
