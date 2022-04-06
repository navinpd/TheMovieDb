package com.api.moviedb.util

const val IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/original"
const val MOVIE_ID_INTENT_EXTRA = "movie_id"
const val SEARCH_QUERY_INTENT_EXTRA = "query"
const val FAV_MOVIE_INTENT_EXTRA = "favorite_movie"
const val GENRE_PREFERENCE_DATA = "GENRE"

class ConstData {
    companion object {
        var genreMap = HashMap<Int?, String?>()
    }
}