package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavMovieStoreUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    RxUseCase<MovieDetail, Boolean> {

    override fun run(params: MovieDetail?): Observable<Boolean> {
        checkNotNull(params) { "The MovieDetail param can not be null" }
        return movieRepository
            .storeFavMovies(params)
            .toObservable()
    }
}
