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
    ): Observable<SearchedMovies>

    fun getMovieDetails(
        id: Int,
    ): Observable<MovieDetail>

    fun getNowPlayingMovie(
        pageNumber: Int,
    ): Observable<NowPlaying>

    fun getPopularMovies(
        pageNumber: Int,
    ): Observable<PopularMovie>

    fun getTopRatedMovies(
        pageNumber: Int,
    ): Observable<TopRatedMovies>

    fun getUpcomingMovies(
        pageNumber: Int,
    ): Observable<UpcomingMovies>

    fun getGenreDetails(
    ): Observable<GeneresResponse>

    fun getFavMovies() : Observable<ArrayList<MovieDetail>>

    fun storeFavMovies(md: MovieDetail)

    fun deleteFavMovie(movieId: Int)
}