package com.api.moviedb.presentation.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.upcoming.UpcomingMovies
import com.api.moviedb.domain.model.PageNumberData
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UpcomingMovieViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel()  {

    var currentPage = 0
    var totalPage = 1

    private val upcomingMovieLiveData = MutableLiveData<UpcomingMovies>()
    val upcomingMoviesMovieData: LiveData<UpcomingMovies>
        get() = upcomingMovieLiveData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState

    fun invokeNextPage() {
        upcomingMovies(page = currentPage + 1)
    }

    fun upcomingMovies(page: Int) {
        if (page in (currentPage + 1)..totalPage) {
            mainUseCase.upcomingUseCase
                .run(params = PageNumberData(page))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { showLoading() }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { hideLoading() }
                .subscribe(
                    { upcomingMoviesRetrieved(it) },
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

    private fun upcomingMoviesRetrieved(upcomingMovies: UpcomingMovies) {
        if (currentPage != upcomingMovies.page) {
            upcomingMovieLiveData.value = upcomingMovies
            currentPage = upcomingMovies.page!!
            totalPage = upcomingMovies.totalPages!!
        }
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        loadingMutableState.postValue(ViewMovieState.ShowError)
    }
}