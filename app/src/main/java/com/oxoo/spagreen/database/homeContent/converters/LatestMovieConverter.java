package com.oxoo.spagreen.database.homeContent.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxoo.spagreen.models.home_content.LatestMovie;

import java.lang.reflect.Type;
import java.util.List;

public class LatestMovieConverter {
    @TypeConverter
    public static String fromList(List<LatestMovie> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<LatestMovie> jsonToList(String value){
        Type listType = new TypeToken<List<LatestMovie>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
