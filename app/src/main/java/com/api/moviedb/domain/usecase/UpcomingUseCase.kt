package com.api.moviedb.domain.usecase

import com.api.moviedb.data.remote.model.upcoming.UpcomingMovies
import com.api.moviedb.domain.model.ModelData
import com.api.moviedb.domain.repository.MovieRepository
import com.api.common.RxUseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpcomingUseCase @Inject constructor(val repository: MovieRepository) :
    RxUseCase<ModelData, UpcomingMovies> {
    override fun run(params: ModelData?): Observable<UpcomingMovies> {
        checkNotNull(params) { "Upcoming data query object can not be null" }

        return repository
            .getUpcomingMovies(params.pageNumber)
            .subscribeOn(Schedulers.io())
    }
}