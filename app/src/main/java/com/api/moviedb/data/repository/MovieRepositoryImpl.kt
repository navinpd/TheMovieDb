package com.api.moviedb.data.repository

import com.api.moviedb.data.local.dao.MovieDetailsDao
import com.api.moviedb.data.local.entity.movieDetails.MovieDetailEntity
import com.api.moviedb.data.mapper.*
import com.api.moviedb.data.remote.api.MovieApi
import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.data.remote.model.nowplaying.NowPlaying
import com.api.moviedb.data.remote.model.popular.PopularMovie
import com.api.moviedb.data.remote.model.searchmovie.SearchedMovies
import com.api.moviedb.data.remote.model.toprated.TopRatedMovies
import com.api.moviedb.data.remote.model.upcoming.UpcomingMovies
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val dbApi: MovieDetailsDao
) : MovieRepository {

    @Inject
    lateinit var movieDataToEntityMapper: MovieDetailDataToEntityMapper

    @Inject
    lateinit var movieDetailEntityToDataMapper : MovieDetailEntityToDetailDataMapper

    override fun searchMovies(
        query: String,
        pageNumber: Int,
    ): Observable<SearchedMovies> =
        movieApi.searchMovies(query = query, page = pageNumber)

    override fun getMovieDetails(
        id: Int
    ): Observable<MovieDetail> =
        movieApi.getMovieDetails(movieId = id)

    override fun getNowPlayingMovie(
        pageNumber: Int,
    ): Observable<NowPlaying> =
        movieApi.getNowPlaying(page = pageNumber)

    override fun getPopularMovies(
        pageNumber: Int
    ): Observable<PopularMovie> =
        movieApi.getPopularMovie(page = pageNumber)

    override fun getTopRatedMovies(
        pageNumber: Int
    ): Observable<TopRatedMovies> =
        movieApi.getTopRatedMovies(page = pageNumber)

    override fun getUpcomingMovies(
        pageNumber: Int
    ): Observable<UpcomingMovies> =
        movieApi.getUpcomingMovies(page = pageNumber)

    override fun getGenreDetails(
    ): Observable<GeneresResponse> =
        movieApi.getGenre()

    override fun getFavMovies(): Observable<ArrayList<MovieDetail>> {
        val movieDetail = movieDetailEntityToDataMapper.map(dbApi.getAllLikedMovies().blockingLast())
        return Observable.just(movieDetail)
    }

    override fun storeFavMovies(md: MovieDetail) {
        val movieDetailEntity = movieDataToEntityMapper.map(md)
        dbApi.insertMovie(movieDetailEntity)
    }

    override fun deleteFavMovie(movieId: Int) {
        dbApi.deleteMovieById(movieId)
    }
}