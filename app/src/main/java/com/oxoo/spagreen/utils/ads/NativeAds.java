package com.oxoo.spagreen.utils.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.NativeBannerAdView;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.database.DatabaseHelper;
import com.oxoo.spagreen.utils.PreferenceUtils;
import com.squareup.picasso.Picasso;
import com.startapp.sdk.ads.nativead.NativeAd;
import com.startapp.sdk.ads.nativead.NativeAdDetails;
import com.startapp.sdk.ads.nativead.NativeAdPreferences;
import com.startapp.sdk.ads.nativead.StartAppNativeAd;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NativeAds {
    private static final String TAG = NativeAds.class.getSimpleName();
    private static UnifiedNativeAd ad = null;

    public NativeAds() {
    }

    public static void showAdmobNativeAds(Activity activity, TemplateView templateView) {
        if (!PreferenceUtils.isActivePlan(activity)) {
            String nativeAdId = new DatabaseHelper(activity).getConfigurationData().getAdsConfig().getAdmobNativeAdsId();
            MobileAds.initialize(activity);
            AdLoader adLoader = new AdLoader.Builder(activity, nativeAdId)
                    .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            ad = unifiedNativeAd;
                            if (unifiedNativeAd != null) {
                                 templateView.setNativeAd(ad);
                            }
                        }
                    }).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        } else {
            templateView.setVisibility(View.GONE);
        }
    }

    public static void releaseAdmobNativeAd() {
        if (ad != null) {
            ad.destroy();
            Log.e(TAG, "Admob Native ad destroyed");
        }
    }

    public static void showFANNativeBannerAd(Activity activity, RelativeLayout container) {
        if (!PreferenceUtils.isActivePlan(activity)) {
            String nativeAdId = new DatabaseHelper(activity).getConfigurationData().getAdsConfig().getFanNativeAdsPlacementId();
            NativeBannerAd nativeBannerAd =
                    new NativeBannerAd(activity, nativeAdId);
            NativeAdListener listener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Log.e(TAG, "FAN Native ad failed to load: " + adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    View adView = NativeBannerAdView.render(activity, nativeBannerAd,
                            NativeBannerAdView.Type.HEIGHT_100);
                    container.addView(adView);
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            };

            //initiate a request to load an ad
            nativeBannerAd.loadAd(
                    nativeBannerAd.buildLoadAdConfig()
                            .withAdListener(listener)
                            .build());
        }
    }

    public void showStartAppNativeAds(Activity activity, RelativeLayout adView) {
        if (!PreferenceUtils.isActivePlan(activity)) {
            String startAppAppId = new DatabaseHelper(activity).getConfigurationData().getAdsConfig().getStartappAppId();
            //String developerId = "165678100";
            String developerId = new DatabaseHelper(activity).getConfigurationData().getAdsConfig().getStartappDeveloperId();
            StartAppAd.init(activity, developerId, startAppAppId);

            StartAppNativeAd startAppNativeAd = new StartAppNativeAd(activity);
            // Declare Native Ad Preferences
            NativeAdPreferences nativePrefs = new NativeAdPreferences()
                    .setAdsNumber(1)                // Load 1 Native Ads
                    .setAutoBitmapDownload(true)    // Retrieve Images object
                    .setPrimaryImageSize(2);        // 150x150 image
            startAppNativeAd.loadAd(nativePrefs, new AdEventListener() {
                @Override
                public void onReceiveAd(com.startapp.sdk.adsbase.Ad ad) {
                    // Native Ad failed to receive
                    List<NativeAdDetails> ads = startAppNativeAd.getNativeAds();    // get NativeAds list
                    if (ads != null) {
                        NativeAdDetails details = ads.get(0);
                        if (details != null){
                            adView.setVisibility(View.VISIBLE);
                            populateViews(details, adView, activity);
                        }else {
                            adView.setVisibility(View.GONE);
                        }
                }
                // Print all ads details to log
                    /*Iterator iterator = ads.iterator();
                    while(iterator.hasNext()){
                        Log.d("StartAppNativeAds", iterator.next().toString());
                    }*/
            }

            @Override
            public void onFailedToReceiveAd (com.startapp.sdk.adsbase.Ad ad){
                // Native Ad failed to receive
                adView.setVisibility(View.GONE);
            }
        });
    }
}

    private void populateViews(NativeAdDetails details, RelativeLayout adView, Activity activity) {
        //image
        ImageView icon = adView.findViewById(R.id.startapp_nativead_image);
        Picasso.get().load(details.getSecondaryImageUrl()).into(icon);
        /*Glide.with(activity)
                .asBitmap()
                .load(details.getImageBitmap())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        icon.setImageBitmap(resource);
                    }
                });*/

        //Log.e("StartAppNativeAds", details.getImageUrl());
        //title
        TextView title = adView.findViewById(R.id.startapp_nativead_title);
        title.setText(details.getTitle());
        //description
        TextView des = adView.findViewById(R.id.startapp_nativead_details);
        des.setText(details.getDescription());
        //ratings
        TextView ratings = adView.findViewById(R.id.startapp_nativead_ratings);
        ratings.setText(String.valueOf(details.getRating()));
        //installs
        TextView installs = adView.findViewById(R.id.startapp_nativead_installs);
        installs.setText(details.getInstalls() + " installs");

        //click
        Button button = adView.findViewById(R.id.startapp_nativead_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String appPackageName = details.getPackacgeName();
                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

    }

}
