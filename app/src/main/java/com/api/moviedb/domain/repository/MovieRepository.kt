package com.api.moviedb.domain.repository

import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.data.remote.model.nowplaying.NowPlayingMovies
import com.api.moviedb.data.remote.model.popular.PopularMovie
import com.api.moviedb.data.remote.model.searchmovie.SearchResultMovies
import com.api.moviedb.data.remote.model.toprated.TopRatedMovies
import com.api.moviedb.data.remote.model.upcoming.UpcomingMovies
import io.reactivex.Completable
import io.reactivex.Observable

interface MovieRepository {

    fun searchMovies(
        query: String, pageNumber: Int,
    ): Observable<SearchResultMovies>

    fun getMovieDetails(
        id: Int,
    ): Observable<MovieDetail>

    fun getNowPlayingMovie(
        pageNumber: Int,
    ): Observable<NowPlayingMovies>

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

    fun storeFavMovies(md: MovieDetail) : Completable

    fun deleteFavMovie(movieId: Int) : Completable

    fun getFavMovie(movieId: Int) : Observable<MovieDetail>

    fun isIdExists(movieId: Int) : Observable<Boolean>
}