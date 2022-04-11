package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.ProductionCountriesData
import com.api.moviedb.data.remote.model.movieDetails.ProductionCountries
import javax.inject.Inject

class ProdCountryEntityToDataMapper @Inject constructor() :
    Mapper<ArrayList<ProductionCountriesData>, ArrayList<ProductionCountries>> {

    override fun map(t: ArrayList<ProductionCountriesData>): ArrayList<ProductionCountries> {
        return ArrayList(
            t.map {
                ProductionCountries(
                    iso31661 = it.iso31661,
                    name = it.name
                )
            }
        )
    }
}