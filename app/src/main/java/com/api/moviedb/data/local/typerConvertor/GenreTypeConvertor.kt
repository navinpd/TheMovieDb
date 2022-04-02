package com.api.moviedb.data.local.typerConvertor

import androidx.room.TypeConverter
import com.api.moviedb.data.local.entity.movieDetails.GenreData
import com.api.moviedb.data.remote.model.genere.Genres
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class GenreTypeConvertor {
    private val gson = GsonBuilder().create()
    private val genreType: Type = object : TypeToken<ArrayList<GenreData>>() {}.type

    @TypeConverter
    fun fromSource(nestedData: ArrayList<GenreData?>?): String? {
        return gson.toJson(nestedData, genreType)
    }

    @TypeConverter
    fun toSource(json: String): ArrayList<GenreData?>? {
        return gson.fromJson<ArrayList<GenreData?>?>(json, genreType)
    }
}