package com.api.moviedb.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.searchmovie.SearchResultMovies
import com.api.moviedb.domain.model.SearchMovieData
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private val searchMovieMLD = MutableLiveData<SearchResultMovies>()
    val searchMovieData: LiveData<SearchResultMovies>
        get() = searchMovieMLD

    private val loadingMutableState = MutableLiveData<ViewMovieState>()
    val loadingState: LiveData<ViewMovieState>
        get() = loadingMutableState

    fun getSearchedResultMovies(query: String, page: Int) {
        mainUseCase.searchMovieUseCase
            .run(SearchMovieData(query = query, page = page))
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideLoading() }
            .subscribe(
                { onMovieSearchRetrieved(it) },
                { onMovieFetchFailed(it) }
            ).disposedBy(compositeDisposable = compositeDisposable)
    }

    override fun showLoading() {
        loadingMutableState.postValue(ViewMovieState.ShowLoading)
    }

    override fun hideLoading() {
        loadingMutableState.postValue(ViewMovieState.HideLoading)
    }

    private fun onMovieSearchRetrieved(movieDetail: SearchResultMovies) {
        searchMovieMLD.value = movieDetail
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        loadingMutableState.postValue(ViewMovieState.ShowError)
    }
}
