package com.api.moviedb.data.remote.model.upcoming


import com.google.gson.annotations.SerializedName


data class Dates(

    @SerializedName("maximum")
    var maximum: String? = null,
    @SerializedName("minimum")
    var minimum: String? = null

)