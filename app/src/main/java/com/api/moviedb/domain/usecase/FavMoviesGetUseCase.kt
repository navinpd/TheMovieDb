package com.api.moviedb.domain.usecase

import android.util.Log
import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class FavMoviesGetUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    RxUseCase<Unit, ArrayList<MovieDetail>> {

    override fun run(params: Unit?): Observable<ArrayList<MovieDetail>> {
        Log.d("TAG", "FavMoviesGet Usecase")
        return movieRepository.getFavMovies()
    }
}
