package com.api.moviedb.domain.usecase

import com.api.common.RxUseCase
import com.api.moviedb.data.remote.model.searchmovie.SearchResultMovies
import com.api.moviedb.domain.model.SearchMovieData
import com.api.moviedb.domain.repository.MovieRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val repository: MovieRepository) :
    RxUseCase<SearchMovieData, SearchResultMovies> {
    override fun run(params: SearchMovieData?): Observable<SearchResultMovies> {
        checkNotNull(params) { "Search Movie Data object can not be null" }
        return repository
            .searchMovies(
                query = params.query,
                pageNumber = params.page
            ).subscribeOn(Schedulers.io())
    }
}