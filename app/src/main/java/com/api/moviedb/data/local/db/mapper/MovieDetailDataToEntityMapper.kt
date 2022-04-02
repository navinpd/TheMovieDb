package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.MovieDetailEntity
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import javax.inject.Inject

class MovieDetailDataToEntityMapper @Inject constructor() :
    Mapper<MovieDetail, MovieDetailEntity> {

    @Inject
    lateinit var geneDataToEntityMapper: GenreDataToEntityMapper

    @Inject
    lateinit var prodCompDataToEntityMapper: ProdCompDataToEntityMapper

    @Inject
    lateinit var prodCountryDataToEntityMapper: ProdCountryDataToEntityMapper

    @Inject
    lateinit var spokenLangDataToEntityMapper: SpokenLangDataToEntityMapper

    override fun map(md: MovieDetail): MovieDetailEntity {
        return MovieDetailEntity(
            id = md.id!!,
            adult = md.adult,
            backdropPath = md.backdropPath,
            belongsToCollection = md.belongsToCollection,
            budget = md.budget,
            homepage = md.homepage,
            imdbId = md.imdbId,
            originalLanguage = md.originalLanguage,
            originalTitle = md.originalTitle,
            overview = md.overview,
            popularity = md.popularity,
            posterPath = md.posterPath,
            genres = geneDataToEntityMapper.map(md.genres),
            productionCompanies = prodCompDataToEntityMapper.map(md.productionCompanies),
            productionCountries = prodCountryDataToEntityMapper.map(md.productionCountries),
            spokenLanguages = spokenLangDataToEntityMapper.map(md.spokenLanguages),
            releaseDate = md.releaseDate,
            revenue = md.revenue,
            runtime = md.runtime,
            status = md.status,
            tagline = md.tagline,
            title = md.title,
            video = md.video,
            voteAverage = md.voteAverage,
            voteCount = md.voteCount
        )
    }
}