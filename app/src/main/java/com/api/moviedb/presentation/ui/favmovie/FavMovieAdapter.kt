package com.api.moviedb.presentation.ui.favmovie

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.api.common.toCommaSeparate
import com.api.common.toDateFormat
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.databinding.AdapterMovieViewBinding
import com.api.moviedb.util.IMAGE_PATH_PREFIX
import com.api.moviedb.util.INextPage
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions


class FavMovieAdapter(
    private val listItem: List<MovieDetail>,
    private val glide: RequestManager
) : RecyclerView.Adapter<FavMovieAdapter.ViewHolder>() {

    lateinit var requestForNextItem: INextPage
    lateinit var context: Context

    inner class ViewHolder(val binding: AdapterMovieViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieViewBinding.inflate(layoutInflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val options = RequestOptions().centerInside()

        if (listItem.isNotEmpty() && position == listItem.size - 1) {
            requestForNextItem.loadNextPage()
        }

        holder.binding.also {
            val result = listItem[position]
            val imagePostfix = result.backdropPath ?: result.posterPath

            glide.load(IMAGE_PATH_PREFIX + imagePostfix)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .apply(options)
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .into(it.movieImage)
            it.titleText.text = result.title
            it.ratingBar.rating = (result.voteAverage!!.div(2)).toFloat()

            it.releaseDate.text = result.releaseDate?.toDateFormat()
            it.voteCountTv.text = result.voteCount?.toCommaSeparate()
            var genre = ""
            result.genres.forEach { genreId ->
                genre =
                    if (genre.isEmpty())
                        genreId.name!!
                    else "$genre, " + genreId.name
            }
            it.genreText.text = genre
            it.cardHolder.setOnClickListener {
                requestForNextItem.getMovieDetails(result.id!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}