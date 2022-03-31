package com.api.moviedb.data.remote.model.toprated

import com.google.gson.annotations.SerializedName


data class TopRatedMovies(

    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var results: ArrayList<Results> = arrayListOf(),
    @SerializedName("total_pages")
    var totalPages: Int? = null,
    @SerializedName("total_results")
    var totalResults: Int? = null

)
