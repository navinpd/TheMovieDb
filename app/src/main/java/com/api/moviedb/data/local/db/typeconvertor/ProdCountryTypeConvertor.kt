package com.api.moviedb.data.local.db.typeconvertor

import androidx.room.TypeConverter
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.local.model.movieDetails.ProductionCountriesData
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ProdCountryTypeConvertor {

    private val productionCountriesType: Type =
        object : TypeToken<ArrayList<ProductionCountriesData>>() {}.type


    @TypeConverter
    fun toSource(json: String): ArrayList<ProductionCountriesData>? {
        return MovieDatabase.gson.fromJson<ArrayList<ProductionCountriesData>>(json, productionCountriesType)
    }

    @TypeConverter
    fun fromSource(nestedData: ArrayList<ProductionCountriesData>): String? {
        return MovieDatabase.gson.toJson(nestedData, productionCountriesType)
    }

}