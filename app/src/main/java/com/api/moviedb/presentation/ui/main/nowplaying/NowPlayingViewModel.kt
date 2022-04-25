package com.api.moviedb.presentation.ui.main.nowplaying

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.nowplaying.NowPlayingMovies
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.domain.model.PageNumberData
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {
    var position: Int = 0

    private var currentPage = 0
    private var totalPage = 1

    private var nowPlayingList = arrayListOf<Results>()

    private val nowPlayingMovieLiveData = MutableLiveData<ArrayList<Results>>()
    val nowPlayingMoviesMovieData: LiveData<ArrayList<Results>>
        get() = nowPlayingMovieLiveData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState

    fun invokeNextPage() {
        getNowPlayingMovies(page = currentPage + 1)
    }

    fun getNowPlayingMovies(page: Int) {
        if (page in (currentPage + 1)..totalPage) {
            mainUseCase.nowPlayingUseCase
                .run(params = PageNumberData(page))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { showLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { hideLoading() }
                .subscribe(
                    { nowPlayingMovieRetrieved(it) },
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

    private fun nowPlayingMovieRetrieved(nowPlayingMovies: NowPlayingMovies) {
        if (currentPage != nowPlayingMovies.page) {
            nowPlayingList = ArrayList(nowPlayingList + nowPlayingMovies.results)
            nowPlayingMovieLiveData.value = nowPlayingList
            currentPage = nowPlayingMovies.page!!
            totalPage = nowPlayingMovies.totalPages!!
        }
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        Log.e("TAG-NowPlaying", throwable.message ?: "")
        loadingMutableState.value = ViewMovieState.ShowError(throwable.message ?: "")
    }
}