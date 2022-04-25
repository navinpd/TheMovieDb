package com.api.moviedb.presentation.ui.main.popular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.data.remote.model.popular.PopularMovie
import com.api.moviedb.domain.model.PageNumberData
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private var currentPage = 0
    private var totalPage = 1

    private var popularMovieList = arrayListOf<Results>()

    private val popularMovieLiveData = MutableLiveData<ArrayList<Results>>()
    val popularMovieData: LiveData<ArrayList<Results>>
        get() = popularMovieLiveData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState

    fun invokeNextPage() {
        getPopularMovies(page = currentPage + 1)
    }

    fun getPopularMovies(page: Int) {
        if (page in (currentPage + 1)..totalPage) {
            mainUseCase.popularUseCase
                .run(params = PageNumberData(page))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { showLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { hideLoading() }
                .subscribe(
                    { onPopularMovieRetrieved(it) },
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

    private fun onPopularMovieRetrieved(popularMovies: PopularMovie) {
        if (currentPage != popularMovies.page) {
            popularMovieList = ArrayList(popularMovieList + popularMovies.results)
            popularMovieLiveData.value = popularMovieList
            currentPage = popularMovies.page!!
            totalPage = popularMovies.totalPages!!
        }
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        Log.e("TAG", throwable.message ?: "")
        loadingMutableState.value = ViewMovieState.ShowError(throwable.message?:"")
    }
}