package com.api.moviedb.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.common.disposedBy
import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.data.remote.model.movieDetails.Genres
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    private val genreData = MutableLiveData<GeneresResponse>()
    val genreState: LiveData<GeneresResponse>
        get() = genreData

    private val loadingMutableState = MutableLiveData<ViewMovieState>()

    fun getGenre() {
        mainUseCase.genreUseCase
            .run()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { showLoading() }
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate { hideLoading() }
            .subscribe(
                { onGenreRetrieved(it) },
                { onGenreFetchFailed(it) }
            ).disposedBy(compositeDisposable)
    }

    override fun showLoading() {
        loadingMutableState.postValue(ViewMovieState.ShowLoading)
    }

    override fun hideLoading() {
        loadingMutableState.postValue(ViewMovieState.HideLoading)
    }

    private fun onGenreRetrieved(genre: GeneresResponse) {
        genreData.value = genre
    }

    private fun onGenreFetchFailed(throwable: Throwable) {
        loadingMutableState.postValue(ViewMovieState.ShowError)
    }
}
