package com.api.moviedb.data.local.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.entity.movieDetails.ProductionCountriesData
import com.api.moviedb.data.remote.model.movieDetails.ProductionCountries
import javax.inject.Inject

class ProdCountryDataToEntityMapper @Inject constructor() :
    Mapper<ArrayList<ProductionCountries>, ArrayList<ProductionCountriesData>> {

    override fun map(t: ArrayList<ProductionCountries>): ArrayList<ProductionCountriesData> {
        val solution = arrayListOf<ProductionCountriesData>()

        for (prod in t) {
            solution.add(
                ProductionCountriesData(
                    iso31661 = prod.iso31661 ?: "",
                    name = prod.name ?: ""
                )
            )
        }

        return solution
    }
}