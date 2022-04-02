package com.api.moviedb.data.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.entity.movieDetails.GenreData
import com.api.moviedb.data.remote.model.movieDetails.Genres
import javax.inject.Inject

class GenreEntityToDataMapper @Inject constructor() : Mapper<ArrayList<GenreData>, ArrayList<Genres>> {
    override fun map(t: ArrayList<GenreData>): ArrayList<Genres> {
        val solution = arrayListOf<Genres>()

        for (genreData in t) {
            solution.add(Genres(genreData.id, genreData.name))
        }

        return solution
    }

}