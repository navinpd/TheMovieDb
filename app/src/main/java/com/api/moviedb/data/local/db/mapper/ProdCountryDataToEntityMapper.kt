package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.ProductionCountriesData
import com.api.moviedb.data.remote.model.movieDetails.ProductionCountries
import javax.inject.Inject

class ProdCountryDataToEntityMapper @Inject constructor() :
    Mapper<ArrayList<ProductionCountries>, ArrayList<ProductionCountriesData>> {

    override fun map(t: ArrayList<ProductionCountries>): ArrayList<ProductionCountriesData> {
        return ArrayList(
            t.map {
                ProductionCountriesData(
                    iso31661 = it.iso31661 ?: "",
                    name = it.name ?: ""
                )
            }
        )
    }
}