package com.api.moviedb.presentation.ui.moviedetail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.api.common.toCommaSeparate
import com.api.common.toDateFormat
import com.api.moviedb.R
import com.api.moviedb.databinding.ActivityMovieDetailBinding
import com.api.moviedb.util.FAV_MOVIE_INTENT_EXTRA
import com.api.moviedb.util.IMAGE_PATH_PREFIX
import com.api.moviedb.util.LIKE_MOVIE_INTENT_EXTRA
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
    private var canLike = true
    private var fromFavMovie = false

    private val viewModel by viewModels<MovieDetailViewModel> {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.getIntExtra(MOVIE_ID_INTENT_EXTRA, 0)
        canLike = intent.getBooleanExtra(LIKE_MOVIE_INTENT_EXTRA, true)
        fromFavMovie = intent.getBooleanExtra(FAV_MOVIE_INTENT_EXTRA, false)

        Log.d("TAG", "MovieId is $movieId")

        val options = RequestOptions().centerInside()
        viewModel.movieDetailState.observe(this) {
            Log.d("TAG", "Movie Details are $it")
            val imagePostfix = IMAGE_PATH_PREFIX + it.backdropPath ?: it.posterPath

            binding.releaseDateTv.text = "Release Date: ${it.releaseDate.toDateFormat()}"

            binding.overviewTv.text = "Overview: ${it.overview}"
            binding.tagLineTv.text = "Tagline: ${it.tagline}"
            binding.titleTv.text = it.title

            binding.ratingBar.rating = (it.voteAverage!!.div(2)).toFloat()
            binding.voteCountTv.text = it.voteCount.toCommaSeparate()
            binding.statusTv.text = "Release Status: ${it.status}"

            var genre = ""
            it.genres.forEach { genres ->
                genre = if (genre.isEmpty()) genres.name!! else genre + ", " + genres.name
            }

            binding.genreTv.text = if (genre.isEmpty()) getString(R.string.genre_hyphen) else "Genre: $genre"
            glide.load(imagePostfix)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .apply(options)
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .into(binding.backdropIv)

            var lang = ""
            it.spokenLanguages.forEach { language ->
                lang =
                    if (lang.isEmpty()) language.englishName!! else "$lang, ${language.englishName}"

            }
            binding.spokenLanguageTv.text = if (lang.isEmpty()) getString(R.string.language_hiphen) else "Language: $lang"

            binding.markFavIv.setOnClickListener { _ ->
                if (canLike) {
                    viewModel.storeFavMovie(it)
                    Toast.makeText(this, "Favorite movie saved", Toast.LENGTH_SHORT).show()
                    binding.markFavIv.setBackgroundResource(R.drawable.ic_baseline_bookmark_white_24)
                } else {
                    viewModel.removeFavMovie(it.id!!)
                    binding.markFavIv.setBackgroundResource(R.drawable.ic_baseline_bookmark_color_24)
                    Toast.makeText(this, "Favorite movie removed", Toast.LENGTH_SHORT).show()
                }
                canLike = !canLike
            }
        }

        if(fromFavMovie) {
            viewModel.getFavMovieFromLocal(movieId)
        } else {
            viewModel.getMovieDetails(movieId)
        }
    }

}