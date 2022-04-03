package com.api.moviedb.presentation.ui.upcoming

import com.api.moviedb.domain.usecase.MainUseCase
import com.api.moviedb.presentation.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class UpcomingMovieViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : BaseViewModel()  {

    fun getUpcomingMovies(pageNum: Int) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}