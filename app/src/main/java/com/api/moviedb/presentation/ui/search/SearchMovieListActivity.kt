package com.api.moviedb.presentation.ui.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.api.moviedb.databinding.ActivitySearchMoviesBinding
import com.api.moviedb.util.INextPage
import com.api.moviedb.util.SEARCH_QUERY_INTENT_EXTRA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMovieListActivity : AppCompatActivity(), INextPage {

    private lateinit var binding: ActivitySearchMoviesBinding
    private var query = ""

    private val viewModel by viewModels<MovieListViewModel> {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = intent.getStringExtra(SEARCH_QUERY_INTENT_EXTRA)?: ""
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleTv.text = "SEARCH MOVIE RESULT"
    }

    override fun loadNextPage() {

    }

    override fun getMovieDetails(movieId: Int) {

    }
}