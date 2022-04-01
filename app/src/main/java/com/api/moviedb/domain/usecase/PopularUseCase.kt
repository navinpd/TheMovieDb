package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.popular.PopularMovie
import com.api.moviedb.domain.model.ModelData
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopularUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    RxUseCase<ModelData, PopularMovie> {

    override fun run(params: ModelData?): Observable<PopularMovie> {
        checkNotNull(params) { "Popular Movie Data object can not be null" }

        return movieRepository
            .getPopularMovies(pageNumber = params.pageNumber)
            .subscribeOn(Schedulers.io())
    }
}
