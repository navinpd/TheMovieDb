package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavMovieExistsUseCase
@Inject constructor(val repository: MovieRepository)
    : RxUseCase<Int, Boolean>{
    override fun run(params: Int?): Observable<Boolean> {
        checkNotNull(params) {
            "movieId can not be null"
        }

        return repository.isIdExists(movieId = params)
    }
}