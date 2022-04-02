package com.api.moviedb.data.local.typerConvertor

import androidx.room.TypeConverter
import com.api.moviedb.data.local.entity.movieDetails.SpokenLanguagesData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SpokenLangTypeConvertor {

    private val gson = Gson()

    private val spokenLanguagesType: Type =
        object : TypeToken<ArrayList<SpokenLanguagesData>>() {}.type

    @TypeConverter
    fun toSource(json: String): ArrayList<SpokenLanguagesData>? {
        return gson.fromJson<ArrayList<SpokenLanguagesData>>(json, spokenLanguagesType)
    }

    @TypeConverter
    fun fromSource(nestedData: ArrayList<SpokenLanguagesData>): String? {
        return gson.toJson(nestedData, spokenLanguagesType)
    }
}