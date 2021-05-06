package com.oxoo.spagreen.database.homeContent;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.oxoo.spagreen.database.homeContent.converters.SliderTypeConverter;
import com.oxoo.spagreen.models.home_content.HomeContent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {HomeContent.class}, exportSchema = false, version = 1)
//@TypeConverters(SliderTypeConverter.class)
public abstract class HomeContentDatabase extends RoomDatabase {
    private static HomeContentDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;

    public abstract HomeContentDao homeContentDao();
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized HomeContentDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    HomeContentDatabase.class, "home_content_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
