package com.api.moviedb.data.local.typerConvertor

import androidx.room.TypeConverter
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.local.entity.movieDetails.ProductionCountriesData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

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