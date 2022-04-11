package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavMovieDeleteUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    RxUseCase<Int, Boolean> {

    override fun run(params: Int?): Observable<Boolean> {
        checkNotNull(params) { "MovieId data can not be null" }

        return movieRepository
            .deleteFavMovie(params)
            .toObservable()
    }

}
