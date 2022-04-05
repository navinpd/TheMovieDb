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
        val movieDetails = arrayListOf<MovieDetail>()
        for (md in t) {
            val movieDetail =
                MovieDetail(
                    id = md.id,
                    voteCount = md.voteCount,
                    voteAverage = md.voteAverage,
                    video = md.video,
                    title = md.title,
                    tagline = md.tagline,
                    status = md.status,
                    runtime = md.runtime,
                    revenue = md.revenue,
                    releaseDate = md.releaseDate,
                    genres = genreEntityToDataMapper.map(md.genres),
                    spokenLanguages = spokenLangEntityToDataMapper.map(md.spokenLanguages),
                    productionCountries = prodCountryEntityToDataMapper.map(md.productionCountries),
                    productionCompanies = prodCompEntityToDataMapper.map(md.productionCompanies),
                    posterPath = md.posterPath,
                    popularity = md.popularity,
                    overview = md.overview,
                    originalTitle = md.originalTitle,
                    originalLanguage = md.originalLanguage,
                    imdbId = md.imdbId,
                    homepage = md.homepage,
                    budget = md.budget,
                    belongsToCollection = belongsCollectionEntityToDataMapper.map(md.belongsToCollection),
                    adult = md.adult,
                    backdropPath = md.backdropPath
                )
            movieDetails.add(movieDetail)
        }
        return movieDetails
    }

}