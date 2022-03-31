package com.api.moviedb.data.repository

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
    private val movieApi: MovieApi
) : MovieRepository {
    override fun searchMovies(
        query: String,
        pageNumber: Int,
        apiKey: String
    ): Observable<SearchedMovies> =
        movieApi.searchMovies(query = query, apiKey = apiKey, page = pageNumber)

    override fun getMovieDetails(
        id: Int,
        apiKey: String
    ): Observable<MovieDetail> =
        movieApi.getMovieDetails(movieId = id, apiKey = apiKey)

    override fun getNowPlayingMovie(
        pageNumber: Int,
        apiKey: String
    ): Observable<NowPlaying> =
        movieApi.getNowPlaying(page = pageNumber, apiKey = apiKey)

    override fun getPopularMovies(
        pageNumber: Int, apiKey: String
    ): Observable<PopularMovie> =
        movieApi.getPopularMovie(page = pageNumber, apiKey = apiKey)

    override fun getTopRatedMovies(
        pageNumber: Int, apiKey: String
    ): Observable<TopRatedMovies> =
        movieApi.getTopRatedMovies(page = pageNumber, apiKey = apiKey)

    override fun getUpcomingMovies(
        pageNumber: Int, apiKey: String
    ): Observable<UpcomingMovies> =
        movieApi.getUpcomingMovies(page = pageNumber, apiKey = apiKey)

    override fun getGenreDetails(
        apiKey: String
    ): Observable<GeneresResponse> =
        movieApi.getGenre(apiKey = apiKey)
}