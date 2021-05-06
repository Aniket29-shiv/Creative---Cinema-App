package com.oxoo.spagreen.database.downlaod;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.oxoo.spagreen.models.DownloadInfo;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DownloadDao {
    @Insert(onConflict = REPLACE)
    void insertDownloadInfo(DownloadInfo download);

    @Update
    void updateDownload(DownloadInfo downloadInfo);

    @Delete
    void deleteDownload(DownloadInfo downloadInfo);

    @Query("DELETE FROM download_table")
    void deleteAll();

    @Query("SELECT * FROM download_table WHERE `download_id`=:id")
    public DownloadInfo getDownloadById(long id);

    @Query("SELECT * FROM download_table")
    LiveData<List<DownloadInfo>> getAllDownloads();
}
