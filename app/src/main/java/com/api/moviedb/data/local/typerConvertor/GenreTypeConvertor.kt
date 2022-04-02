package com.api.moviedb.data.local.typerConvertor

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.local.entity.movieDetails.GenreData
import com.api.moviedb.data.remote.model.genere.Genres
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

@ProvidedTypeConverter
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