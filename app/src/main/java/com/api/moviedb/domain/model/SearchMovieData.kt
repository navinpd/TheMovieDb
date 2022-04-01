package com.api.moviedb.domain.model

data class SearchMovieData(
    val query: String,
    val page: Int,
)