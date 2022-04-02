package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavMovieGet @Inject constructor(private val movieRepository: MovieRepository) :
    RxUseCase<Int, ArrayList<MovieDetail>> {

    override fun run(params: Int?): Observable<ArrayList<MovieDetail>> {
        checkNotNull(params) {"MovieId to getFavMovie can not be null"}

        return movieRepository.getFavMovies()
    }
}
