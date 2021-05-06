package com.oxoo.spagreen.database.continueWatching;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "continue_watching")
public class ContinueWatchingModel {

    @PrimaryKey
    @ColumnInfo(name = "content_id")
    @NonNull
    private String contentId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "image_url")
    private String imgUrl;

    @ColumnInfo(name = "progress")
    private float progress;

    @ColumnInfo(name = "position")
    private long position;

    @ColumnInfo(name = "stream_url")
    private String streamUrl;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "v_type")
    public String vType;

    public ContinueWatchingModel() {
    }

    public ContinueWatchingModel(@NonNull String contentId, String name, String imgUrl, float progress, long position, String streamURL, String type, String vType) {
        this.contentId = contentId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.progress = progress;
        this.position = position;
        this.streamUrl = streamURL;
        this.type = type;
        this.vType = vType;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }


    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamURL) {
        this.streamUrl = streamURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    public String getContentId() {
        return contentId;
    }

    public void setContentId(@NonNull String contentId) {
        this.contentId = contentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
