package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.toprated.TopRatedMovies
import com.api.moviedb.domain.model.PageNumberData
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopRatedUseCase @Inject constructor(val repository: MovieRepository) :
    RxUseCase<PageNumberData, TopRatedMovies> {
    override fun run(params: PageNumberData?): Observable<TopRatedMovies> {
        checkNotNull(params)

        return repository
            .getTopRatedMovies(pageNumber = params.pageNumber)
            .subscribeOn(Schedulers.io())
    }
}