package com.api.moviedb.data.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.entity.movieDetails.ProductionCountriesData
import com.api.moviedb.data.remote.model.movieDetails.ProductionCountries
import javax.inject.Inject

class ProdCountryEntityToDataMapper @Inject constructor() :
    Mapper<ArrayList<ProductionCountriesData>, ArrayList<ProductionCountries>> {

    override fun map(t: ArrayList<ProductionCountriesData>): ArrayList<ProductionCountries> {
        val solution = arrayListOf<ProductionCountries>()

        for (sol in t) {
            solution.add(
                ProductionCountries(
                    iso31661 = sol.iso31661,
                    name = sol.name
                )
            )
        }

        return solution
    }
}