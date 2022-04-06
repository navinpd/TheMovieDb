package com.api.moviedb.presentation.ui.favmovie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.databinding.ActivityLikedMoviesBinding
import com.api.moviedb.presentation.ui.moviedetail.MovieDetailActivity
import com.api.moviedb.util.FAV_MOVIE_INTENT_EXTRA
import com.api.moviedb.util.INextPage
import com.api.moviedb.util.MOVIE_ID_INTENT_EXTRA
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavMovieListActivity : AppCompatActivity(), INextPage {

    private lateinit var binding: ActivityLikedMoviesBinding

    @Inject
    lateinit var glide: RequestManager

    private val viewModel by viewModels<FavMovieListViewModel> {
        defaultViewModelProviderFactory
    }

    var currentDataSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "FavMovieListActivity")

        binding = ActivityLikedMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favList = mutableListOf<MovieDetail>()
        val adapter = FavMovieAdapter(favList, glide)
        adapter.requestForNextItem = this

        binding.titleTv.text = getString(R.string.favourite_movies)

        val recyclerView = binding.likedMoviesRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.movieListLD.observe(this) {
            if (it.size != currentDataSize) {
                if (it.size > 0) {
                    favList.clear()
                    favList.addAll(it)
                    adapter.notifyDataSetChanged()
                    binding.likedMoviesRv.visibility = View.VISIBLE
                    binding.noSearchResult.visibility = View.GONE
                } else {
                    binding.likedMoviesRv.visibility = View.GONE
                    binding.noSearchResult.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.getFavMovieList()
    }

    override fun loadNextPage() {
        //No need to load more pages.
    }

    override fun getMovieDetails(movieId: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID_INTENT_EXTRA, movieId)
        intent.putExtra(FAV_MOVIE_INTENT_EXTRA, true)
        startActivity(intent)
    }
}