package com.api.moviedb.data.local.db.typeconvertor

import androidx.room.TypeConverter
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.local.model.movieDetails.GenreData
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class GenreTypeConvertor {
    private val genreType: Type = object : TypeToken<ArrayList<GenreData>>() {}.type

    @TypeConverter
    fun fromSource(nestedData: ArrayList<GenreData?>?): String? {
        return MovieDatabase.gson.toJson(nestedData, genreType)
    }

    @TypeConverter
    fun toSource(json: String): ArrayList<GenreData?>? {
        return MovieDatabase.gson.fromJson<ArrayList<GenreData?>?>(json, genreType)
    }
}