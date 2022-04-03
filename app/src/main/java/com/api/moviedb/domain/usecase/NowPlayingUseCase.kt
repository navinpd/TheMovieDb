package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.nowplaying.NowPlayingMovies
import com.api.moviedb.domain.model.PageNumberData
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NowPlayingUseCase @Inject constructor
    (private val movieRepository: MovieRepository) :
    RxUseCase<PageNumberData, NowPlayingMovies> {

    override fun run(params: PageNumberData?): Observable<NowPlayingMovies> {
        checkNotNull(params) { "Data object can not be null" }

        return movieRepository
            .getNowPlayingMovie(pageNumber = params.pageNumber)
            .subscribeOn(Schedulers.io())
    }
}
