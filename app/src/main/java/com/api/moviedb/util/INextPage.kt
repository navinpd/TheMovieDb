package com.api.moviedb.util

interface INextPage {
    fun loadNextPage()

    fun getMovieDetails(movieId: Int)
}