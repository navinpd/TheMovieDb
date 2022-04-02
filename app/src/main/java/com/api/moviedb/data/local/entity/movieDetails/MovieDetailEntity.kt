package com.api.moviedb.data.local.entity.movieDetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie_Detail")
data class MovieDetailEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean? = false,
    val backdropPath: String? = "",
    val belongsToCollection: String? = "",
    val budget: Int? = 0,
    val homepage: String? = "",
    val imdbId: String? = "",
    val originalLanguage: String? = "",
    val originalTitle: String? = "",
    val overview: String? = null,
    val popularity: Double? = 0.0,
    val posterPath: String? = null,
    val genres: ArrayList<GenreData>,
    val productionCompanies: ArrayList<ProductionCompaniesData>,
    val productionCountries: ArrayList<ProductionCountriesData>,
    val spokenLanguages: ArrayList<SpokenLanguagesData>,
    val releaseDate: String? = null,
    val revenue: Int? = 0,
    val runtime: Int? = 0,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = false,
    val voteAverage: Double? = 0.0,
    val voteCount: Int? = 0
)