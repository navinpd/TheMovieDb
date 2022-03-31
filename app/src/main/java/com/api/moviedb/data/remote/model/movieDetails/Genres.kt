package com.api.moviedb.data.remote.model.movieDetails


import com.google.gson.annotations.SerializedName

data class Genres(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null

)
