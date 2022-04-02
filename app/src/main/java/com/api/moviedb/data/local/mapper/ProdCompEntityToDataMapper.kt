package com.api.moviedb.data.local.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.entity.movieDetails.ProductionCompaniesData
import com.api.moviedb.data.remote.model.movieDetails.ProductionCompanies
import javax.inject.Inject

class ProdCompEntityToDataMapper @Inject constructor() :
    Mapper<ArrayList<ProductionCompaniesData>, ArrayList<ProductionCompanies>> {

    override fun map(t: ArrayList<ProductionCompaniesData>): ArrayList<ProductionCompanies> {
        val solution = arrayListOf<ProductionCompanies>()

        for (prod in t) {
            solution.add(
                ProductionCompanies(
                    id = prod.id,
                    logoPath = prod.logoPath,
                    name = prod.name,
                    originCountry = prod.originCountry,
                )
            )
        }

        return solution
    }

}