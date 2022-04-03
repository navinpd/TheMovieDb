package com.api.moviedb.data.local.db.typeconvertor

import androidx.room.TypeConverter
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.local.model.movieDetails.SpokenLanguagesData
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SpokenLangTypeConvertor {

    private val spokenLanguagesType: Type =
        object : TypeToken<ArrayList<SpokenLanguagesData>>() {}.type

    @TypeConverter
    fun toSource(json: String): ArrayList<SpokenLanguagesData>? {
        return MovieDatabase.gson.fromJson<ArrayList<SpokenLanguagesData>>(json, spokenLanguagesType)
    }

    @TypeConverter
    fun fromSource(nestedData: ArrayList<SpokenLanguagesData>): String? {
        return MovieDatabase.gson.toJson(nestedData, spokenLanguagesType)
    }
}