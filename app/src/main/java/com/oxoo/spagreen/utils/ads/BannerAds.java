package com.oxoo.spagreen.utils.ads;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


import android.provider.ContactsContract;
import android.widget.RelativeLayout;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.ixidev.gdpr.GDPRChecker;
import com.oxoo.spagreen.database.DatabaseHelper;
import com.oxoo.spagreen.network.model.config.AdsConfig;
import com.oxoo.spagreen.utils.PreferenceUtils;
import com.startapp.sdk.ads.banner.Mrec;
import com.startapp.sdk.adsbase.StartAppAd;


public class BannerAds {
    public static void ShowAdmobBannerAds(Activity context, RelativeLayout mAdViewLayout) {
        if (!PreferenceUtils.isActivePlan(context)) {
            AdsConfig adsConfig = new DatabaseHelper(context).getConfigurationData().getAdsConfig();

            AdView mAdView = new AdView(context);
            mAdView.setAdSize(AdSize.BANNER);
            mAdView.setAdUnitId(adsConfig.getAdmobBannerAdsId());
            AdRequest.Builder builder = new AdRequest.Builder();
            GDPRChecker.Request request = GDPRChecker.getRequest();

            if (request == GDPRChecker.Request.NON_PERSONALIZED) {
                // load non Personalized ads
                Bundle extras = new Bundle();
                extras.putString("npa", "1");
                builder.addNetworkExtrasBundle(AdMobAdapter.class, extras);
            } // else do nothing , it will load PERSONALIZED ads
            mAdView.loadAd(builder.build());
            mAdViewLayout.addView(mAdView);
        }
    }

    public static void showStartAppBanner(Activity context, final RelativeLayout mainLayout) {
        if (!PreferenceUtils.isActivePlan(context)) {
            String startAppAppId = new DatabaseHelper(context).getConfigurationData().getAdsConfig().getStartappAppId();
            //String developerId = "165678100";
            String developerId = new DatabaseHelper(context).getConfigurationData().getAdsConfig().getStartappDeveloperId();
            StartAppAd.init(context,developerId, startAppAppId);
            // Define StartApp Mrec
            Mrec startAppMrec = new Mrec(context);
            RelativeLayout.LayoutParams mrecParameters =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            mrecParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mrecParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            // Add to main Layout
            mainLayout.addView(startAppMrec, mrecParameters);
        }
    }

    public static void showFANBanner(Activity context, RelativeLayout mAdViewLayout) {
        if (!PreferenceUtils.isActivePlan(context)) {
            AdsConfig adsConfig = new DatabaseHelper(context).getConfigurationData().getAdsConfig();

            com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, adsConfig.getFanBannerAdsPlacementId(), com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            /*this is for test ad*/
            //com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", com.facebook.ads.AdSize.BANNER_HEIGHT_50);

            mAdViewLayout.addView(adView);
            // Request an ad
            adView.loadAd();
        }
    }
}