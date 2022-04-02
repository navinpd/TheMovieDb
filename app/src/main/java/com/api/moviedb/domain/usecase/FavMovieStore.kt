package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable

class FavMovieStore(private val movieRepository: MovieRepository) :
    RxUseCase<MovieDetail, Boolean> {

    override fun run(params: MovieDetail?): Observable<Boolean> {
        checkNotNull(params) { "The MovieDetail param can not be null" }
        movieRepository
            .storeFavMovies(params)

        return Observable.just(true)
    }
}
