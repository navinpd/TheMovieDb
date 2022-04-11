package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.GenreData
import com.api.moviedb.data.remote.model.movieDetails.Genres
import javax.inject.Inject

class GenreDataToEntityMapper @Inject constructor() :
    Mapper<ArrayList<Genres>, ArrayList<GenreData>> {

    override fun map(t: ArrayList<Genres>): ArrayList<GenreData> {
        return ArrayList(t.map { GenreData(id = it.id ?: 0, name = it.name ?: "") })
    }
}