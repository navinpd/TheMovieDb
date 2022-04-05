package com.api.moviedb.presentation.ui.favmovie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "FavMovieListActivity")

        binding = ActivityLikedMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val favList = mutableListOf<MovieDetail>()
        val adapter = FavMovieAdapter(favList, glide)
        adapter.requestForNextItem = this

        val recyclerView = binding.likedMoviesRv
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.movieListLD.observe(this) {
            favList.addAll(it)
            adapter.notifyItemRangeChanged(0, it.size)
        }

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