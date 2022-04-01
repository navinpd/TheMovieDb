package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.model.MovieDetailsData
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(private val repository: MovieRepository) :
    RxUseCase<MovieDetailsData, MovieDetail> {

    override fun run(params: MovieDetailsData?): Observable<MovieDetail> {
        checkNotNull(params) { "Movie Details object can not be null" }
        return repository
            .getMovieDetails(id = params.movieId)
            .subscribeOn(Schedulers.io())
    }

}