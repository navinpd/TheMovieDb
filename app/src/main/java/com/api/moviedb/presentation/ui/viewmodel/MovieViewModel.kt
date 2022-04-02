package com.api.moviedb.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.model.MovieDetailsData
import com.api.moviedb.domain.usecase.MainUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private val movieDetailData = MutableLiveData<MovieDetail>()
    val movieDetailState: LiveData<MovieDetail>
        get() = movieDetailData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState


    fun getMovieDetails(movieId: Int) {
        mainUseCase.movieDetailUseCase
            .run(MovieDetailsData(movieId))
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideLoading() }
            .subscribe(
                { onMovieDetailRetrieved(it) },
                { onMovieFetchFailed(it) }
            ).disposedBy(compositeDisposable)
    }

    private fun showLoading() {
        loadingMutableState.postValue(ViewMovieState.ShowLoading)
    }

    private fun hideLoading() {
        loadingMutableState.postValue(ViewMovieState.HideLoading)
    }

    private fun onMovieDetailRetrieved(movieDetail: MovieDetail) {
        movieDetailData.value = movieDetail
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        loadingMutableState.postValue(ViewMovieState.ShowError)
    }

    fun getTopRatedMovies() {

    }

    fun getUpcomingMovies() {

    }

    fun getPopularMovies() {

    }

    fun getNowPlayingMovies() {

    }

    fun getSearchResultMovies() {

    }

}


sealed class ViewMovieState {
    object ShowLoading : ViewMovieState()
    object HideLoading : ViewMovieState()
    object ShowError : ViewMovieState()
}
