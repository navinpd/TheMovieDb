package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.model.MovieIdData
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavMovieGetUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    RxUseCase<MovieIdData, MovieDetail> {

    override fun run(params: MovieIdData?): Observable<MovieDetail> {
        checkNotNull(params) {
            "movieId can not be null"
        }

        return movieRepository.getFavMovie(params.movieId)
    }
}