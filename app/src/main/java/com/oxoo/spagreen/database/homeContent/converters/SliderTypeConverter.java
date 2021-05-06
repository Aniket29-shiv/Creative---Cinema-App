package com.oxoo.spagreen.database.homeContent.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oxoo.spagreen.models.home_content.Slider;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SliderTypeConverter {
    @androidx.room.TypeConverter
    public static String fromArrayList(Slider slider){
        Gson gson = new Gson();
        return gson.toJson(slider);
    }

    @TypeConverter
    public static Slider jsonToList(String value){
        Type listType = new TypeToken<Slider>() {}.getType();

        Gson gson = new Gson();
        return gson.fromJson(value, listType);
    }
}
