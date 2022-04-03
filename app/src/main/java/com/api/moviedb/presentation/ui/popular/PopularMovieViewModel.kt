package com.api.moviedb.presentation.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.popular.PopularMovie
import com.api.moviedb.domain.model.PageNumberData
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PopularMovieViewModel  @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel()  {

    private val popularMovieLiveData = MutableLiveData<PopularMovie>()
    val popularMovieData: LiveData<PopularMovie>
        get() = popularMovieLiveData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState

    fun getPopularMovies(page: Int) {
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

    override fun showLoading() {
        loadingMutableState.postValue(ViewMovieState.ShowLoading)
    }

    override fun hideLoading() {
        loadingMutableState.postValue(ViewMovieState.HideLoading)
    }

    private fun onPopularMovieRetrieved(popularMovies: PopularMovie) {
        popularMovieLiveData.value = popularMovies
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        loadingMutableState.postValue(ViewMovieState.ShowError)
    }
}