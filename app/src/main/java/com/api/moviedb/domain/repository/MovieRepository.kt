package com.api.moviedb.domain.repository

import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.data.remote.model.nowplaying.NowPlaying
import com.api.moviedb.data.remote.model.popular.PopularMovie
import com.api.moviedb.data.remote.model.searchmovie.SearchedMovies
import com.api.moviedb.data.remote.model.toprated.TopRatedMovies
import com.api.moviedb.data.remote.model.upcoming.UpcomingMovies
import io.reactivex.Observable

interface MovieRepository {

    fun searchMovies(
        query: String, pageNumber: Int,
        apiKey: String
    ): Observable<SearchedMovies>

    fun getMovieDetails(
        id: Int,
        apiKey: String
    ): Observable<MovieDetail>

    fun getNowPlayingMovie(
        pageNumber: Int,
        apiKey: String
    ): Observable<NowPlaying>

    fun getPopularMovies(
        pageNumber: Int,
        apiKey: String
    ): Observable<PopularMovie>

    fun getTopRatedMovies(
        pageNumber: Int,
        apiKey: String
    ): Observable<TopRatedMovies>

    fun getUpcomingMovies(
        pageNumber: Int,
        apiKey: String
    ): Observable<UpcomingMovies>

    fun getGenreDetails(
        apiKey: String
    ): Observable<GeneresResponse>
}