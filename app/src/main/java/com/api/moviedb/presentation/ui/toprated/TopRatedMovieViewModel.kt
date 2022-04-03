package com.api.moviedb.presentation.ui.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.searchmovie.SearchedMovies
import com.api.moviedb.data.remote.model.toprated.TopRatedMovies
import com.api.moviedb.domain.model.PageNumberData
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TopRatedMovieViewModel  @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private val topRatedMovieLiveData = MutableLiveData<TopRatedMovies>()
    val topRatedMovieData: LiveData<TopRatedMovies>
        get() = topRatedMovieLiveData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState

    fun getTopRatedMovies(page: Int) {
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

    override fun showLoading() {
        loadingMutableState.postValue(ViewMovieState.ShowLoading)
    }

    override fun hideLoading() {
        loadingMutableState.postValue(ViewMovieState.HideLoading)
    }

    private fun onTopRatedMovieRetrieved(topMovies: TopRatedMovies) {
        topRatedMovieLiveData.value = topMovies
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        loadingMutableState.postValue(ViewMovieState.ShowError)
    }
}