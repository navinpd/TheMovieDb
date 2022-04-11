package com.api.moviedb.data.repository

import android.util.Log
import com.api.moviedb.data.local.db.dao.MovieDetailsDao
import com.api.moviedb.data.local.db.mapper.MovieDetailDataToEntityMapper
import com.api.moviedb.data.local.db.mapper.MovieEntityToDataMapper
import com.api.moviedb.data.local.db.mapper.MovieListEntityToMovieListDataMapper
import com.api.moviedb.data.remote.api.MovieApi
import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.data.remote.model.nowplaying.NowPlayingMovies
import com.api.moviedb.data.remote.model.popular.PopularMovie
import com.api.moviedb.data.remote.model.searchmovie.SearchResultMovies
import com.api.moviedb.data.remote.model.toprated.TopRatedMovies
import com.api.moviedb.data.remote.model.upcoming.UpcomingMovies
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val dbApi: MovieDetailsDao,
    private val movieDataToEntityMapper: MovieDetailDataToEntityMapper,
    private val movieListEntityToDataMapper: MovieListEntityToMovieListDataMapper,
    private val movieEntityToDataMapper: MovieEntityToDataMapper
) : MovieRepository {

    override fun searchMovies(
        query: String,
        pageNumber: Int,
    ): Observable<SearchResultMovies> =
        movieApi.searchMovies(query = query, page = pageNumber)

    override fun getMovieDetails(
        id: Int
    ): Observable<MovieDetail> =
        movieApi.getMovieDetails(movieId = id)

    override fun getNowPlayingMovie(
        pageNumber: Int,
    ): Observable<NowPlayingMovies> =
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
        val data = dbApi.getAllLikedMovies().blockingFirst()
        val movieDetail = movieListEntityToDataMapper
            .map(data)
        return Observable.just(movieDetail)
    }

    override fun storeFavMovies(md: MovieDetail) : Completable {
        val movieDetailEntity = movieDataToEntityMapper.map(md)
        return dbApi.insertMovie(movieDetailEntity)
    }

    override fun deleteFavMovie(movieId: Int) : Completable {
        return dbApi.deleteMovieById(movieId)
    }

    override fun getFavMovie(movieId: Int): Observable<MovieDetail> {
        return Observable.just(
            movieEntityToDataMapper.map(
                dbApi.getFavMovie(movieId).blockingFirst()
            )
        )
    }
}