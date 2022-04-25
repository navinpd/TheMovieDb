package com.api.moviedb.presentation.ui.moviedetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.model.MovieIdData
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private val movieDetailData = MutableLiveData<MovieDetail>()
    val movieDetailState: LiveData<MovieDetail>
        get() = movieDetailData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState

    private val isMovieStoredMutableState = MutableLiveData<Boolean>()
    val localMovieStoredState: LiveData<Boolean>
        get() = isMovieStoredMutableState

    fun getMovieDetails(movieId: Int) {
        mainUseCase.movieDetailUseCase
            .run(MovieIdData(movieId))
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideLoading() }
            .subscribe(
                { onMovieDetailRetrieved(it) },
                { onMovieFetchFailed(it) }
            ).disposedBy(compositeDisposable)
    }

    fun isFavMovieStored(movieId: Int?) {
        mainUseCase.favMovieExistsUseCase
            .run(movieId)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideLoading() }
            .subscribe(
                { favMovieStored(it) },
                { onMovieFetchFailed(it) }
            ).disposedBy(compositeDisposable)
    }

    fun getFavMovieFromLocal(movieId: Int) {
        Observable.defer {
            mainUseCase.favMovieGetUseCase
                .run(MovieIdData(movieId))
        }.subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideLoading() }
            .subscribe(
                { onMovieDetailRetrieved(it) },
                { onMovieFetchFailed(it) }
            ).disposedBy(compositeDisposable)
    }

    fun storeFavMovie(favMovie: MovieDetail) {
        Observable.defer {
            mainUseCase.favMovieStoreUseCase
                .run(favMovie)
        }.subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideLoading() }
            .subscribe()
            .disposedBy(compositeDisposable)
    }

    fun removeFavMovie(moveId: Int) {
        Observable.defer {
            mainUseCase.favMovieDeleteUseCase
                .run(moveId)
        }.subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideLoading() }
            .subscribe()
            .disposedBy(compositeDisposable)
    }

    override fun showLoading() {
        loadingMutableState.postValue(ViewMovieState.ShowLoading)
    }

    override fun hideLoading() {
        loadingMutableState.postValue(ViewMovieState.HideLoading)
    }

    private fun onMovieDetailRetrieved(movieDetail: MovieDetail) {
        movieDetailData.value = movieDetail
    }

    private fun favMovieStored(isStored : Boolean) {
        isMovieStoredMutableState.value = isStored
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        Log.e("TAG", throwable.message.toString())
        loadingMutableState.value = ViewMovieState.ShowError(throwable.message?:"")
    }
}