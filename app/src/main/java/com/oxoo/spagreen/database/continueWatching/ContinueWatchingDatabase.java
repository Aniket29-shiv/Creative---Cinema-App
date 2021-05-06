package com.oxoo.spagreen.database.continueWatching;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {ContinueWatchingModel.class}, exportSchema = false, version = 3)
public abstract class ContinueWatchingDatabase extends RoomDatabase {

    private static ContinueWatchingDatabase instance;

    public abstract ContinueWatchingDao continueWatchingDao();

    public static synchronized ContinueWatchingDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContinueWatchingDatabase.class, "Content_Watching_DB").
                    fallbackToDestructiveMigration().
                    build();
        }
        return instance;
    }
}
