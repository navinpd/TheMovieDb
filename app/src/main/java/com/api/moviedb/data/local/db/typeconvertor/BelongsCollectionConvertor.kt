package com.api.moviedb.data.local.db.typeconvertor

import androidx.room.TypeConverter
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.local.model.movieDetails.BelongsCollectionData
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BelongsCollectionConvertor {
    private val collectionData: Type = object : TypeToken<BelongsCollectionData>() {}.type

    @TypeConverter
    fun fromSource(nestedData: BelongsCollectionData?): String? {
        return MovieDatabase.gson.toJson(nestedData, collectionData)
    }

    @TypeConverter
    fun toSource(json: String): BelongsCollectionData? {
        return MovieDatabase.gson.fromJson<BelongsCollectionData?>(json, collectionData)
    }
}