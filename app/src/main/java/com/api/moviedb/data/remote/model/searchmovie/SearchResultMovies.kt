package com.api.moviedb.data.remote.model.searchmovie

import com.api.moviedb.data.remote.model.nowplaying.Results
import com.google.gson.annotations.SerializedName


data class SearchResultMovies(

    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var results: ArrayList<Results> = arrayListOf(),
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null

)
