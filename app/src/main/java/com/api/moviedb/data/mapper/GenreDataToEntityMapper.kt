package com.api.moviedb.data.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.entity.movieDetails.GenreData
import com.api.moviedb.data.remote.model.movieDetails.Genres
import javax.inject.Inject

class GenreDataToEntityMapper @Inject constructor() : Mapper<ArrayList<Genres>, ArrayList<GenreData>> {

    override fun map(t: ArrayList<Genres>): ArrayList<GenreData> {
        val solution = arrayListOf<GenreData>()

        for (genres in t) {
            solution.add(GenreData(id = genres.id ?: 0, name = genres.name ?: ""))
        }

        return solution
    }
}