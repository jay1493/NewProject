package com.anubhav_singh.infoedgeassignment.database.typeConverters;

import android.arch.persistence.room.TypeConverter;

import com.anubhav_singh.infoedgeassignment.models.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Anubhav-Singh on 06-02-2018.
 */

public class CategoryListConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Category> categoryStringToList(String data){
        if(data!=null){

            Type listType = new TypeToken<List<Category>>() {}.getType();

            return gson.fromJson(data, listType);
        }
        return null;
    }

    @TypeConverter
    public static String categoryListToString(List<Category> data){
        if(data!=null) {
            return gson.toJson(data);
        }
        return null;
    }

}
