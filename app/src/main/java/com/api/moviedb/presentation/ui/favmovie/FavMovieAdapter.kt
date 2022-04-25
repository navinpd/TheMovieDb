package com.api.moviedb.presentation.ui.favmovie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.api.common.toCommaSeparator
import com.api.common.toDateFormat
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.movieDetails.MovieDetail
import com.api.moviedb.databinding.AdapterMovieViewBinding
import com.api.moviedb.util.ConstData
import com.api.moviedb.util.ConstData.Companion.loadImageToHolder
import com.api.moviedb.util.IMAGE_PATH_PREFIX
import com.api.moviedb.util.INextPage
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions


class FavMovieAdapter(
    private val glide: RequestManager
) : RecyclerView.Adapter<FavMovieAdapter.ViewHolder>() {

    lateinit var requestForNextItem: INextPage
    lateinit var context: Context

    private val diffCallback = object : DiffUtil.ItemCallback<MovieDetail>() {
        override fun areItemsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDetail, newItem: MovieDetail): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(val binding: AdapterMovieViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieViewBinding.inflate(layoutInflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    fun submitList(item: List<MovieDetail>) {
        differ.submitList(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (differ.currentList.isNotEmpty() && position == differ.currentList.size - 1) {
            requestForNextItem.loadNextPage()
        }

        holder.binding.run {
            val result = differ.currentList[position]
            val imagePath = IMAGE_PATH_PREFIX + (result.backdropPath ?: result.posterPath)

            loadImageToHolder(glide = glide, imagePath = imagePath, movieImage =  movieImage)

            titleText.text = result.title
            ratingBar.rating = (result.voteAverage!!.div(2)).toFloat()

            releaseDate.text =
                context.getString(R.string.release_date, result.releaseDate?.toDateFormat())
            voteCountTv.text = result.voteCount?.toCommaSeparator()

            genreText.text = ConstData.getGenreNames(genres = result.genres, context = context)

            cardHolder.setOnClickListener {
                requestForNextItem.getMovieDetails(result.id!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}