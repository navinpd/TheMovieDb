package com.api.moviedb.data.local.typerConvertor

import androidx.room.TypeConverter
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.local.entity.movieDetails.ProductionCompaniesData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

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