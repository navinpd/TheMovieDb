package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GenreUseCase
@Inject constructor(private val movieRepository: MovieRepository) :
    RxUseCase<Unit, GeneresResponse> {
    override fun run(params: Unit?): Observable<GeneresResponse> {

        return movieRepository
            .getGenreDetails()
            .subscribeOn(Schedulers.io())
    }
}
