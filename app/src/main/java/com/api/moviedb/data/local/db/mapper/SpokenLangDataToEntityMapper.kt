package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.SpokenLanguagesData
import com.api.moviedb.data.remote.model.movieDetails.SpokenLanguages
import javax.inject.Inject

class SpokenLangDataToEntityMapper @Inject constructor() :
    Mapper<ArrayList<SpokenLanguages>, ArrayList<SpokenLanguagesData>> {

    override fun map(t: ArrayList<SpokenLanguages>): ArrayList<SpokenLanguagesData> {
        val solution = arrayListOf<SpokenLanguagesData>()

        for (lang in t) {
            solution.add(
                SpokenLanguagesData(
                    englishName = lang.englishName ?: "",
                    iso6391 = lang.iso6391 ?: "",
                    name = lang.name ?: ""
                )
            )
        }

        return solution
    }
}