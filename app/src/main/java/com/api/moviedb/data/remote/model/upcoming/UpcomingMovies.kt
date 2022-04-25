package com.api.moviedb.data.remote.model.upcoming

import com.api.moviedb.data.remote.model.BaseMovies
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.google.gson.annotations.SerializedName


data class UpcomingMovies(

    @SerializedName("dates")
    var dates: Dates? = Dates(),

    @SerializedName("page")
    override var page: Int? = null,

    @SerializedName("results")
    override var results: ArrayList<Results> = arrayListOf(),

    @SerializedName("total_pages")
    override var totalPages: Int? = null,

    @SerializedName("total_results")
    override var totalResults: Int? = null

) : BaseMovies()