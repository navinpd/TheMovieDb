package com.api.moviedb.data.local.db.typerConvertor

import androidx.room.TypeConverter
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.local.model.movieDetails.ProductionCompaniesData
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ProdCompanyTypeConvertor {

    private val productionCompaniesType: Type =
        object : TypeToken<ArrayList<ProductionCompaniesData>>() {}.type


    @TypeConverter
    fun toSource(json: String): ArrayList<ProductionCompaniesData>? {
        return MovieDatabase.gson.fromJson<ArrayList<ProductionCompaniesData>>(json, productionCompaniesType)
    }

    @TypeConverter
    fun fromSource(nestedData: ArrayList<ProductionCompaniesData>): String? {
        return MovieDatabase.gson.toJson(nestedData, productionCompaniesType)
    }

}