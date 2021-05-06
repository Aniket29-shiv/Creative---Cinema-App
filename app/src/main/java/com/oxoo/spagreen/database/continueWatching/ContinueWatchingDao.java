package com.oxoo.spagreen.database.continueWatching;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContinueWatchingDao {
    @Insert(onConflict = REPLACE)
    void insertInfo(ContinueWatchingModel continueWatchingModel);

    @Update
    void updateInfo(ContinueWatchingModel continueWatchingModel);

    @Query("DELETE FROM continue_watching")
    void deleteAll();

    @Delete
    void delete(ContinueWatchingModel continueWatchingModel);

    @Query("SELECT * from continue_watching where `Content_ID`=:id")
    public ContinueWatchingModel getContent(String id);

    @Query("SELECT * from continue_watching")
    LiveData<List<ContinueWatchingModel>> getAllContent();
}
