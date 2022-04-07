package com.api.moviedb.presentation.ui.main.toprated

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.toprated.TopRatedMovies
import com.api.moviedb.domain.model.PageNumberData
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TopRatedMovieViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    var currentPage = 0
    var totalPage = 1

    private val topRatedMovieLiveData = MutableLiveData<TopRatedMovies>()
    val topRatedMovieData: LiveData<TopRatedMovies>
        get() = topRatedMovieLiveData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState

    fun invokeNextPage() {
        getTopRatedMovies(page = currentPage + 1)
    }

    fun getTopRatedMovies(page: Int) {
        if (page in (currentPage + 1)..totalPage) {
            mainUseCase.topRatedUseCase
                .run(params = PageNumberData(page))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { showLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { hideLoading() }
                .subscribe(
                    { onTopRatedMovieRetrieved(it) },
                    { onMovieFetchFailed(it) }
                ).disposedBy(compositeDisposable = compositeDisposable)
        }
    }

    override fun showLoading() {
        loadingMutableState.postValue(ViewMovieState.ShowLoading)
    }

    override fun hideLoading() {
        loadingMutableState.postValue(ViewMovieState.HideLoading)
    }

    private fun onTopRatedMovieRetrieved(topMovies: TopRatedMovies) {
        if (currentPage != topMovies.page) {
            topRatedMovieLiveData.value = topMovies
            currentPage = topMovies.page!!
            totalPage = topMovies.totalPages!!
        }
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        Log.e("TAG-TopRated", throwable.message!!)
        loadingMutableState.value = ViewMovieState.ShowError(throwable.message ?: "")
    }
}