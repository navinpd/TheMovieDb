package com.api.moviedb.presentation.ui.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.api.common.toCommaSeparate
import com.api.common.toDateFormat
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.movieDetails.Genres
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.data.remote.model.movieDetails.SpokenLanguages
import com.api.moviedb.databinding.ActivityMovieDetailBinding
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
import com.api.moviedb.util.FAV_MOVIE_INTENT_EXTRA
import com.api.moviedb.util.IMAGE_PATH_PREFIX
import com.api.moviedb.util.MOVIE_ID_INTENT_EXTRA
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager

    private var _binding: ActivityMovieDetailBinding? = null
    private val binding get() = _binding!!
    private var movieId = 0
    private var saveToLocal = true
    private var fromFavMovie = false

    private val viewModel by viewModels<MovieDetailViewModel> {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.getIntExtra(MOVIE_ID_INTENT_EXTRA, 0)
        fromFavMovie = intent.getBooleanExtra(FAV_MOVIE_INTENT_EXTRA, false)

        Log.d("TAG", "MovieId is $movieId")

        val options = RequestOptions().centerInside()
        viewModel.movieDetailState.observe(this) {
            val imagePath = IMAGE_PATH_PREFIX + (it.backdropPath ?: it.posterPath)

            binding.run {
                releaseDateTv.text = getString(R.string.release_date, it.releaseDate.toDateFormat())
                overviewTv.text = getString(R.string.overview_text, it.overview)
                tagLineTv.text = getString(R.string.tagline_text, it.tagline)
                titleTv.text = it.title
                ratingBar.rating = (it.voteAverage!!.div(2)).toFloat()
                voteCountTv.text = it.voteCount.toCommaSeparate()
                statusTv.text = getString(R.string.release_text, it.status)
                genreTv.text = getGenre(it.genres)

                glide.load(imagePath)
                    .error(R.drawable.ic_baseline_error_24)
                    .apply(options)
                    .placeholder(R.drawable.ic_baseline_downloading_24)
                    .into(backdropIv)

                spokenLanguageTv.text = spokenLanguage(it.spokenLanguages)
            }

            binding.markFavIv.setOnClickListener { _ ->
                processDbOperation(it)
            }

            updateLocalMovieState()
        }

        viewModel.loadingState.observe(this) {
            when (it) {
                is ViewMovieState.ShowError -> {
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = it.message
                }

                is ViewMovieState.HideLoading -> {
                    binding.progressBar.visibility = View.GONE
                }

                is ViewMovieState.ShowLoading -> {
                    binding.errorMessage.text = getString(R.string.no_data_found)
                    binding.errorMessage.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        invokeMovieDetails()
    }

    private fun getGenre(genres: ArrayList<Genres>): String {
        var genre = ""
        genres.forEach { gen ->
            genre = if (genre.isEmpty()) gen.name!! else genre + ", " + gen.name
        }

        if (genre.isEmpty()) getString(R.string.genre_hyphen) else getString(
            R.string.genre_data,
            genre
        )
        return genre
    }

    private fun spokenLanguage(it: ArrayList<SpokenLanguages>): String {
        var lang = ""
        it.forEach { language ->
            lang =
                if (lang.isEmpty()) language.englishName!! else "$lang, ${language.englishName}"
        }
        if (lang.isEmpty()) getString(R.string.language_hyphen) else getString(
            R.string.language_data,
            lang
        )
        return lang
    }

    private fun processDbOperation(it: MovieDetail) {
        if (saveToLocal) {
            viewModel.storeFavMovie(it)
            Toast.makeText(this, getString(R.string.store_fav_movie), Toast.LENGTH_SHORT)
                .show()
            binding.markFavIv.setBackgroundResource(R.drawable.ic_baseline_bookmark_white_24)
        } else {
            viewModel.removeFavMovie(it.id!!)
            binding.markFavIv.setBackgroundResource(R.drawable.ic_baseline_bookmark_color_24)
            Toast.makeText(this, getString(R.string.removed_fav_movie), Toast.LENGTH_SHORT)
                .show()
        }
        saveToLocal = !saveToLocal
    }

    private fun invokeMovieDetails() {
        if (fromFavMovie) {
            viewModel.getFavMovieFromLocal(movieId)
        } else {
            viewModel.getMovieDetails(movieId)
        }
    }

    private fun updateLocalMovieState() {
        if (fromFavMovie) {
            saveToLocal = false
            binding.markFavIv.setBackgroundResource(R.drawable.ic_baseline_bookmark_white_24)
        }
    }
}