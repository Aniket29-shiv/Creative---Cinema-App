package com.oxoo.spagreen.utils.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.internal.$Gson$Preconditions;
import com.oxoo.spagreen.database.DatabaseHelper;
import com.oxoo.spagreen.network.model.config.AdsConfig;
import com.oxoo.spagreen.utils.PreferenceUtils;
import com.startapp.sdk.adsbase.StartAppAd;


public class PopUpAds {

    public static void ShowAdmobInterstitialAds(Activity context) {
        if (!PreferenceUtils.isActivePlan(context)) {
            AdsConfig adsConfig = new DatabaseHelper(context).getConfigurationData().getAdsConfig();
            final InterstitialAd mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(adsConfig.getAdmobInterstitialAdsId());
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                    mInterstitialAd.show();

                /*Random rand = new Random();
                int i = rand.nextInt(10)+1;
                Log.e("INTER AD:", String.valueOf(i));
                if (i%2==0){
                    mInterstitialAd.show();
                }*/
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);

                }
            });
        }
    }

    public static void showFANInterstitialAds(Activity context) {
        final String TAG = "FAN";
        if (!PreferenceUtils.isActivePlan(context)) {
            DatabaseHelper db = new DatabaseHelper(context);
            String placementId = db.getConfigurationData().getAdsConfig().getFanInterstitialAdsPlacementId();

            final com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(context, placementId);
            InterstitialAdListener listener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial ad displayed callback
                    Log.e(TAG, "Interstitial ad displayed.");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    // Interstitial dismissed callback
                    Log.e(TAG, "Interstitial ad dismissed.");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Ad error callback
                    Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Interstitial ad is loaded and ready to be displayed
                    Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                    // Show the ad
                    interstitialAd.show();
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                    Log.d(TAG, "Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
                    Log.d(TAG, "Interstitial ad impression logged!");
                }
            };

            interstitialAd.loadAd(interstitialAd.buildLoadAdConfig()
                    .withAdListener(listener)
                    .build());
        }

    }

    public static void showStartappInterstitialAds(Activity context) {
        if (!PreferenceUtils.isActivePlan(context)) {
            String startAppAppId = new DatabaseHelper(context).getConfigurationData().getAdsConfig().getStartappAppId();
            //String developerId = "165678100";
            String developerId = new DatabaseHelper(context).getConfigurationData().getAdsConfig().getStartappDeveloperId();
            StartAppAd.init(context,developerId, startAppAppId);
            //startapp
            StartAppAd.showAd(context); // show the ad

        }
    }

}