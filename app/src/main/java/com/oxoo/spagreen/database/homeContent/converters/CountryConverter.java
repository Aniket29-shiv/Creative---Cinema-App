package com.oxoo.spagreen.database.homeContent.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxoo.spagreen.models.home_content.AllCountry;

import java.lang.reflect.Type;
import java.util.List;

public class CountryConverter {
    @TypeConverter
    public static String fromArrayList(List<AllCountry> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<AllCountry> jsonToList(String value){
        Type listType = new TypeToken<List<AllCountry>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
