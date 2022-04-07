package com.api.moviedb.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    abstract fun showLoading()

    abstract fun hideLoading()
}

sealed class ViewMovieState {
    object ShowLoading : ViewMovieState()
    object HideLoading : ViewMovieState()
    data class ShowError(var message: String) : ViewMovieState()
}