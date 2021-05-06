package com.oxoo.spagreen.database.homeContent.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxoo.spagreen.models.home_content.FeaturesGenreAndMovie;

import java.lang.reflect.Type;
import java.util.List;

public class FeaturesGenreConverter {
    @TypeConverter
    public static String fromArrayList(List<FeaturesGenreAndMovie> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<FeaturesGenreAndMovie> jsonToList(String value){
        Type listType = new TypeToken<List<FeaturesGenreAndMovie>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
