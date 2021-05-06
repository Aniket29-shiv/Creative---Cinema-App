package com.oxoo.spagreen.database.homeContent.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxoo.spagreen.models.home_content.LatestTvseries;

import java.lang.reflect.Type;
import java.util.List;

public class TvSeriesConverter {
    @TypeConverter
    public static String fromList(List<LatestTvseries> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<LatestTvseries> jsonToList(String value){
        Type listType = new TypeToken<List<LatestTvseries>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
