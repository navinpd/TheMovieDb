package com.api.moviedb.presentation.ui.favmovielist

import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class FavMovieListViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel() {

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    fun getFavMovies(pageNum: Int) {

    }
}