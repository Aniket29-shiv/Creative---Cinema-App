package com.oxoo.spagreen.database.homeContent.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxoo.spagreen.models.home_content.Video;

import java.lang.reflect.Type;
import java.util.List;

public class VideoConverter {
    @TypeConverter
    public static String fromList(List<Video> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Video> jsonToList(String value){
        Type listType = new TypeToken<List<Video>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
