package com.oxoo.spagreen.utils;

import android.content.Context;
import android.net.Uri;

import androidx.mediarouter.app.MediaRouteButton;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.cast.CastPlayer;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.common.images.WebImage;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ChromeCastUtil implements CastPlayer.SessionAvailabilityListener {

    private CastContext castContext;
    private static CastPlayer castPlayer;

    public void initCast(MediaRouteButton mediaRouteButton, Context context) {
        CastButtonFactory.setUpMediaRouteButton(context.getApplicationContext(), mediaRouteButton);
        castContext = CastContext.getSharedInstance(context);
        castPlayer = new CastPlayer(castContext);
        castPlayer.setSessionAvailabilityListener(this);

    }

    @Override
    public void onCastSessionAvailable() {

    }

    @Override
    public void onCastSessionUnavailable() {

    }
}
