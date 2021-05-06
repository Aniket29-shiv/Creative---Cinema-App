package com.oxoo.spagreen.database.homeContent.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxoo.spagreen.models.home_content.PopularStars;

import java.lang.reflect.Type;
import java.util.List;

public class PopularStarsConverter {
    @TypeConverter
    public static String fromArrayList(List<PopularStars> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<PopularStars> jsonToList(String value){
        Type listType = new TypeToken<List<PopularStars>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
