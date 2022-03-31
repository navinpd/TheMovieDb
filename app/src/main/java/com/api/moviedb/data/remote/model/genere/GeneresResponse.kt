package com.api.moviedb.data.remote.model.genere

import com.google.gson.annotations.SerializedName


data class GeneresResponse(

    @SerializedName("genres")
    var genres: ArrayList<Genres> = arrayListOf()


)
