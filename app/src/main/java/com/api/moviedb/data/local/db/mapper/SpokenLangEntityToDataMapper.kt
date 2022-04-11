package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.SpokenLanguagesData
import com.api.moviedb.data.remote.model.movieDetails.SpokenLanguages
import javax.inject.Inject

class SpokenLangEntityToDataMapper @Inject constructor() :
    Mapper<ArrayList<SpokenLanguagesData>, ArrayList<SpokenLanguages>> {

    override fun map(t: ArrayList<SpokenLanguagesData>): ArrayList<SpokenLanguages> {
        return ArrayList(t.map {
            SpokenLanguages(
                iso6391 = it.iso6391,
                englishName = it.englishName,
                name = it.name
            )
        })
    }

}