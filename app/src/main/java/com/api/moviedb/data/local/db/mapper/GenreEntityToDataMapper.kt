package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.GenreData
import com.api.moviedb.data.remote.model.movieDetails.Genres
import javax.inject.Inject

class GenreEntityToDataMapper @Inject constructor() : Mapper<ArrayList<GenreData>, ArrayList<Genres>> {
    override fun map(t: ArrayList<GenreData>): ArrayList<Genres> {

        return ArrayList(t.map { Genres(it.id, it.name) })
    }

}