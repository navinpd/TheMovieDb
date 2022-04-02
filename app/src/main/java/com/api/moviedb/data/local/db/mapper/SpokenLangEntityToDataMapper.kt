package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.SpokenLanguagesData
import com.api.moviedb.data.remote.model.movieDetails.SpokenLanguages
import javax.inject.Inject

class SpokenLangEntityToDataMapper @Inject constructor() :
    Mapper<ArrayList<SpokenLanguagesData>, ArrayList<SpokenLanguages>> {

    override fun map(t: ArrayList<SpokenLanguagesData>): ArrayList<SpokenLanguages> {
        val solution = arrayListOf<SpokenLanguages>()

        for (sol in t) {
            solution.add(
                SpokenLanguages(
                    iso6391 = sol.iso6391,
                    englishName = sol.englishName,
                    name = sol.name
                )
            )
        }

        return solution
    }

}