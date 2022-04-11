package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.ProductionCompaniesData
import com.api.moviedb.data.remote.model.movieDetails.ProductionCompanies
import javax.inject.Inject

class ProdCompEntityToDataMapper @Inject constructor() :
    Mapper<ArrayList<ProductionCompaniesData>, ArrayList<ProductionCompanies>> {

    override fun map(t: ArrayList<ProductionCompaniesData>): ArrayList<ProductionCompanies> {

        return ArrayList(
            t.map {
                ProductionCompanies(
                    id = it.id,
                    logoPath = it.logoPath,
                    name = it.name,
                    originCountry = it.originCountry,
                )
            }
        )
    }

}