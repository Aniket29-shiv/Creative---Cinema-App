package com.oxoo.spagreen.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;

import androidx.core.view.ViewCompat;

import com.oxoo.spagreen.AppConfig;

import java.util.Locale;

public class RtlUtils {

    public static void setScreenDirection(Context context){
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        String language = config.locale.getLanguage();
        if (AppConfig.ENABLE_RTL){
            if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                config.setLayoutDirection(new Locale(language));
            } else {
                config.setLayoutDirection(new Locale("en"));
            }
        }else {
            //use ltr
            config.setLayoutDirection(new Locale("en"));
        }
    }
}
