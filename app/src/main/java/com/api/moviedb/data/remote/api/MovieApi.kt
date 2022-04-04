package com.api.moviedb.data.remote.api

import com.api.moviedb.data.remote.EndPoints.API_KEY_STRING
import com.api.moviedb.data.remote.EndPoints.ID
import com.api.moviedb.data.remote.EndPoints.MOVIE_DETAIL_URL
import com.api.moviedb.data.remote.EndPoints.MOVIE_LIST_URL
import com.api.moviedb.data.remote.EndPoints.NOW_PLAYING_URL
import com.api.moviedb.data.remote.EndPoints.PAGE
import com.api.moviedb.data.remote.EndPoints.POPULAR_URL
import com.api.moviedb.data.remote.EndPoints.QUERY
import com.api.moviedb.data.remote.EndPoints.SEARCH_MOVIES_URL
import com.api.moviedb.data.remote.EndPoints.TOP_RATED_URL
import com.api.moviedb.data.remote.EndPoints.UPCOMING_MOVIES_URL
import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.data.remote.model.nowplaying.NowPlayingMovies
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

    @GET(MOVIE_LIST_URL)
    fun getGenre(
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<GeneresResponse>

    @GET("$MOVIE_DETAIL_URL/{id}")
    fun getMovieDetails(
        @Path(ID) movieId: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<MovieDetail>

    @GET(SEARCH_MOVIES_URL)
    fun searchMovies(
        @Query(QUERY) query: String,
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<SearchedMovies>

    @GET(NOW_PLAYING_URL)
    fun getNowPlaying(
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<NowPlayingMovies>


    @GET(POPULAR_URL)
    fun getPopularMovie(
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<PopularMovie>


    @GET(TOP_RATED_URL)
    fun getTopRatedMovies(
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<TopRatedMovies>


    @GET(UPCOMING_MOVIES_URL)
    fun getUpcomingMovies(
        @Query(PAGE) page: Int,
        @Query(API_KEY_STRING) apiKey: String = API_KEY,
    ): Observable<UpcomingMovies>

}