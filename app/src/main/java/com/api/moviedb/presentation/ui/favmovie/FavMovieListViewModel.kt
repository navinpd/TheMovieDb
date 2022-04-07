package com.api.moviedb.presentation.ui.favmovie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FavMovieListViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private val movieListMLiveData = MutableLiveData<ArrayList<MovieDetail>>()
    val movieListLD: LiveData<ArrayList<MovieDetail>>
        get() = movieListMLiveData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState

    fun getFavMovieList() {
        Observable.defer {
            mainUseCase.favMoviesListGetUseCase
                .run()
        }.subscribeOn(Schedulers.io())
            .doOnSubscribe {}
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { }
            .subscribe(
                { onMovieDetailRetrieved(it) },
                { onMovieFetchFailed(it) }
            ).disposedBy(compositeDisposable)
    }

    private fun onMovieDetailRetrieved(movieDetail: ArrayList<MovieDetail>) {
        movieListMLiveData.value = movieDetail
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        Log.e("TAG", throwable.message ?: "")
        loadingMutableState.value = ViewMovieState.ShowError(throwable.message?:"")
    }

    override fun showLoading() {
        loadingMutableState.postValue(ViewMovieState.ShowLoading)
    }

    override fun hideLoading() {
        loadingMutableState.postValue(ViewMovieState.HideLoading)
    }

}