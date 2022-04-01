package com.api.moviedb.data.remote.api

import com.api.moviedb.data.remote.EntPoints.API_KEY_STRING
import com.api.moviedb.data.remote.EntPoints.ID
import com.api.moviedb.data.remote.EntPoints.MOVIE_DETAIL
import com.api.moviedb.data.remote.EntPoints.MOVIE_LIST
import com.api.moviedb.data.remote.EntPoints.NOW_PLAYING
import com.api.moviedb.data.remote.EntPoints.PAGE
import com.api.moviedb.data.remote.EntPoints.POPULAR
import com.api.moviedb.data.remote.EntPoints.QUERY
import com.api.moviedb.data.remote.EntPoints.SEARCH_MOVIES
import com.api.moviedb.data.remote.EntPoints.TOP_RATED
import com.api.moviedb.data.remote.EntPoints.UPCOMING_MOVIES
import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.data.remote.model.nowplaying.NowPlaying
import com.api.moviedb.data.remote.model.popular.PopularMovie
import com.api.moviedb.data.remote.model.searchmovie.SearchedMovies
import com.api.moviedb.data.remote.model.toprated.TopRatedMovies
import com.api.moviedb.data.remote.model.upcoming.UpcomingMovies
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    companion object {
        private const val API_KEY = "0e7274f05c36db12cbe71d9ab0393d47"
    }

    @GET(MOVIE_LIST)
    fun getGenre(
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<GeneresResponse>

    @GET("$MOVIE_DETAIL/{id}")
    fun getMovieDetails(
        @Path(ID) movieId: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<MovieDetail>

    @GET("$SEARCH_MOVIES/{query}")
    fun searchMovies(
        @Path(QUERY) query: String,
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<SearchedMovies>

    @GET(NOW_PLAYING)
    fun getNowPlaying(
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<NowPlaying>


    @GET(POPULAR)
    fun getPopularMovie(
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<PopularMovie>


    @GET(TOP_RATED)
    fun getTopRatedMovies(
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<TopRatedMovies>


    @GET(UPCOMING_MOVIES)
    fun getUpcomingMovies(
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<UpcomingMovies>

}