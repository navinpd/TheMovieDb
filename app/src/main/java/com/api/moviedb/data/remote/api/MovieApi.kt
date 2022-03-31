package com.api.moviedb.data.remote.api

import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.data.remote.model.nowplaying.NowPlaying
import com.api.moviedb.data.remote.model.popular.PopularMovie
import com.api.moviedb.data.remote.model.searchmovie.SearchedMovies
import com.api.moviedb.data.remote.model.toprated.TopRatedMovies
import com.api.moviedb.data.remote.model.upcoming.UpcomingMovies
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

abstract class MovieApi {

    @GET("/3/genre/movie/list")
    abstract fun getGenre(
        @Query("api_key") apiKey: String,
    ): Observable<GeneresResponse>

    @GET("/3/movie")
    abstract fun getMovieDetails(
        @Query("api_key") apiKey: String,
        @Body movieId: Int
    ): Observable<MovieDetail>

    @GET("/3/search/movie")
    abstract fun searchMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
        @Body query: String,
    ): Observable<SearchedMovies>

    @GET("/3/movie/now_playing")
    abstract fun getNowPlaying(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): Observable<NowPlaying>


    @GET("/3/movie/popular")
    abstract fun getPopularMovie(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): Observable<PopularMovie>


    @GET("/3/movie/top_rated")
    abstract fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): Observable<TopRatedMovies>


    @GET("/3/movie/upcoming")
    abstract fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
    ): Observable<UpcomingMovies>

}