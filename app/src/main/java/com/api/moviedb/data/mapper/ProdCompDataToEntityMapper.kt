package com.api.moviedb.data.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.entity.movieDetails.ProductionCompaniesData
import com.api.moviedb.data.remote.model.movieDetails.ProductionCompanies
import javax.inject.Inject

class ProdCompDataToEntityMapper @Inject constructor() :
    Mapper<ArrayList<ProductionCompanies>, ArrayList<ProductionCompaniesData>> {
    override fun map(t: ArrayList<ProductionCompanies>): ArrayList<ProductionCompaniesData> {
        val solution = arrayListOf<ProductionCompaniesData>()

        for (prod in t) {
            solution.add(
                ProductionCompaniesData(
                    id = prod.id ?: 0,
                    logoPath = prod.logoPath ?: "",
                    name = prod.name ?: "",
                    originCountry = prod.originCountry ?: ""
                )
            )
        }

        return solution
    }
}