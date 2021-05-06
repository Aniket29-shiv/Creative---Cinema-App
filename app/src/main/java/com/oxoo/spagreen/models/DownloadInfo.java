package com.oxoo.spagreen.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "download_table")
public class DownloadInfo {
    @PrimaryKey
    @ColumnInfo(name = "download_id")
    @NonNull
    private long downloadId;

    @ColumnInfo(name = "file_name")
    private String fileName;

    @ColumnInfo(name = "percentage")
    private int percentage;

    public DownloadInfo(long downloadId, String fileName, int percentage) {
        this.downloadId = downloadId;
        this.fileName = fileName;
        this.percentage = percentage;
    }

    public long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(long downloadId) {
        this.downloadId = downloadId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
