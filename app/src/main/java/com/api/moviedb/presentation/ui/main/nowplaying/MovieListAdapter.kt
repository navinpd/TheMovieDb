package com.api.moviedb.presentation.ui.main.nowplaying

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.api.common.toCommaSeparator
import com.api.common.toDateFormat
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.databinding.AdapterMovieViewBinding
import com.api.moviedb.util.ConstData
import com.api.moviedb.util.ConstData.Companion.genreMap
import com.api.moviedb.util.IMAGE_PATH_PREFIX
import com.api.moviedb.util.INextPage
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions

class MovieListAdapter(
    private val glide: RequestManager,
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    lateinit var requestForNextItem: INextPage
    lateinit var context: Context

    inner class ViewHolder(val binding: AdapterMovieViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun submitList(item: List<Results>) {
        differ.submitList(differ.currentList + item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieViewBinding.inflate(layoutInflater, parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (differ.currentList.isNotEmpty() && position == differ.currentList.size - 1) {
            requestForNextItem.loadNextPage()
        }

        holder.binding.run {
            val result = differ.currentList[position]
            val imagePath = IMAGE_PATH_PREFIX + (result.backdropPath ?: result.posterPath)

            ConstData.loadImageToHolder(
                glide = glide,
                imagePath = imagePath,
                movieImage = movieImage
            )

            titleText.text = result.title
            ratingBar.rating = (result.voteAverage!!.div(2)).toFloat()

            releaseDate.text =
                context.getString(R.string.release_date, result.releaseDate?.toDateFormat())
            voteCountTv.text = result.voteCount?.toCommaSeparator()

            genreText.text = ConstData.getGenreFromId(data = result.genreIds, context = context)

            cardHolder.setOnClickListener {
                requestForNextItem.getMovieDetails(result.id!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}