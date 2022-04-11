package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.ProductionCompaniesData
import com.api.moviedb.data.remote.model.movieDetails.ProductionCompanies
import javax.inject.Inject

class ProdCompDataToEntityMapper @Inject constructor() :
    Mapper<ArrayList<ProductionCompanies>, ArrayList<ProductionCompaniesData>> {

    override fun map(t: ArrayList<ProductionCompanies>): ArrayList<ProductionCompaniesData> {

        return ArrayList(t.map {
            ProductionCompaniesData(
                id = it.id ?: 0,
                logoPath = it.logoPath ?: "",
                name = it.name ?: "",
                originCountry = it.originCountry ?: ""
            )
        })
    }
}