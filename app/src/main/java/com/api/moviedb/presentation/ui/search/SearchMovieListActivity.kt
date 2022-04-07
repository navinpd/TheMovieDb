package com.api.moviedb.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.databinding.ActivitySearchMoviesBinding
import com.api.moviedb.presentation.ui.main.nowplaying.MovieListAdapter
import com.api.moviedb.presentation.ui.moviedetail.MovieDetailActivity
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import com.api.moviedb.util.INextPage
import com.api.moviedb.util.MOVIE_ID_INTENT_EXTRA
import com.api.moviedb.util.SEARCH_QUERY_INTENT_EXTRA
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchMovieListActivity : AppCompatActivity(), INextPage {

    private lateinit var binding: ActivitySearchMoviesBinding
    private var query = ""

    @Inject
    lateinit var glide: RequestManager

    private val viewModel by viewModels<MovieListViewModel> {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = intent.getStringExtra(SEARCH_QUERY_INTENT_EXTRA) ?: ""
        binding = ActivitySearchMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchRv = binding.nowPlayingRv
        val searchResult = mutableListOf<Results>()
        val adapter = MovieListAdapter(searchResult, glide)
        adapter.requestForNextItem = this
        searchRv.adapter = adapter
        searchRv.layoutManager = LinearLayoutManager(this)

        binding.titleTv.text = getString(R.string.search_movie_result)
        viewModel.searchMovieData.observe(this) {
            val size = it.results.size
            Log.d("TAG", "Data size is $size")
            if (size == 0) {
                binding.noSearchResult.visibility = View.VISIBLE
                binding.noSearchResult.text = getString(R.string.no_data_found)
                binding.nowPlayingRv.visibility = View.GONE
            } else {
                binding.noSearchResult.visibility = View.GONE
                binding.nowPlayingRv.visibility = View.VISIBLE
                searchResult.addAll(it.results)
                adapter.notifyItemRangeChanged(size, size + it.results.size)
            }
        }

        viewModel.loadingState.observe(this) {
            when (it) {
                is ViewMovieState.ShowError -> {
                    binding.noSearchResult.visibility = View.VISIBLE
                    binding.noSearchResult.text = it.message
                }

                is ViewMovieState.HideLoading -> {
                    binding.progressBar.visibility = View.GONE
                }

                is ViewMovieState.ShowLoading -> {
                    binding.noSearchResult.text = getString(R.string.no_data_found)
                    binding.noSearchResult.visibility
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.getSearchedResultMovies(query, 1)
    }

    override fun loadNextPage() {
        viewModel.invokeNextPage(query = query)
    }

    override fun getMovieDetails(movieId: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID_INTENT_EXTRA, movieId)
        startActivity(intent)
    }
}