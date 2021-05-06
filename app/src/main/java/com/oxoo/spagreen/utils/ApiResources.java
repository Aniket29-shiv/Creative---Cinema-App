package com.oxoo.spagreen.utils;

import com.oxoo.spagreen.AppConfig;
import com.oxoo.spagreen.network.RetrofitClient;

public class ApiResources {

    public static String CURRENCY; // must be valid currency code
    public static String EXCHSNGE_RATE;
    public static String PAYPAL_CLIENT_ID;
    public static String RAZORPAY_EXCHANGE_RATE;
    public static String USER_PHONE;


    String URL = AppConfig.API_SERVER_URL + RetrofitClient.API_URL_EXTENSION;


    String searchUrl = URL+"search";

    String getAllReply = URL+"all_replay";
    String termsURL = AppConfig.TERMS_URL;

    public String getTermsURL() {
        return termsURL;
    }

    public String getGetAllReply() {
        return getAllReply;
    }

    public String getSearchUrl() {
        return searchUrl;
    }


    }


