package com.api.moviedb.data.remote.model

import com.api.moviedb.data.remote.model.nowplaying.Results

abstract class BaseMovies {

    abstract var page: Int?

    abstract var results: ArrayList<Results>

    abstract var totalPages: Int?

    abstract var totalResults: Int?
}