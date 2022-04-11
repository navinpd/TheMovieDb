package com.api.moviedb.data.local.db.mapper

import com.api.common.Mapper
import com.api.moviedb.data.local.model.movieDetails.MovieDetailEntity
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import javax.inject.Inject

class MovieListEntityToMovieListDataMapper @Inject constructor() :
    Mapper<Array<MovieDetailEntity>, ArrayList<MovieDetail>> {

    @Inject
    lateinit var genreEntityToDataMapper: GenreEntityToDataMapper

    @Inject
    lateinit var prodCompEntityToDataMapper: ProdCompEntityToDataMapper

    @Inject
    lateinit var prodCountryEntityToDataMapper: ProdCountryEntityToDataMapper

    @Inject
    lateinit var spokenLangEntityToDataMapper: SpokenLangEntityToDataMapper

    @Inject
    lateinit var belongsCollectionEntityToDataMapper: BelongsCollectionEntityToDataMapper

    override fun map(t: Array<MovieDetailEntity>): ArrayList<MovieDetail> {
        return ArrayList(t.map {
            MovieDetail(
                id = it.id,
                voteCount = it.voteCount,
                voteAverage = it.voteAverage,
                video = it.video,
                title = it.title,
                tagline = it.tagline,
                status = it.status,
                runtime = it.runtime,
                revenue = it.revenue,
                releaseDate = it.releaseDate,
                genres = genreEntityToDataMapper.map(it.genres),
                spokenLanguages = spokenLangEntityToDataMapper.map(it.spokenLanguages),
                productionCountries = prodCountryEntityToDataMapper.map(it.productionCountries),
                productionCompanies = prodCompEntityToDataMapper.map(it.productionCompanies),
                posterPath = it.posterPath,
                popularity = it.popularity,
                overview = it.overview,
                originalTitle = it.originalTitle,
                originalLanguage = it.originalLanguage,
                imdbId = it.imdbId,
                homepage = it.homepage,
                budget = it.budget,
                belongsToCollection = belongsCollectionEntityToDataMapper.map(it.belongsToCollection),
                adult = it.adult,
                backdropPath = it.backdropPath
            )
        })
    }

}