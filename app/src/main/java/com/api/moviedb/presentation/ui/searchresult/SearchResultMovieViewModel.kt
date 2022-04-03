package com.api.moviedb.presentation.ui.searchresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.searchmovie.SearchedMovies
import com.api.moviedb.domain.model.SearchMovieData
import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchResultMovieViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private val searchMovieMLD = MutableLiveData<SearchedMovies>()
    val searchMovieData: LiveData<SearchedMovies>
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

    private fun onMovieSearchRetrieved(movieDetail: SearchedMovies) {
        searchMovieMLD.value = movieDetail
    }

    private fun onMovieFetchFailed(throwable: Throwable) {
        loadingMutableState.postValue(ViewMovieState.ShowError)
    }
}
