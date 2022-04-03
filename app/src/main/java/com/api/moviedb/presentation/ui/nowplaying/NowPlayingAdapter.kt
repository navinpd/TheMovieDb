package com.api.moviedb.presentation.ui.nowplaying

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.databinding.MovieViewAdapterBinding
import com.api.moviedb.util.IMAGE_PATH_PREFIX
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions

class NowPlayingAdapter(
    private val listItem: List<Results>,
    private val glide: RequestManager
) : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: MovieViewAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieViewAdapterBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val options = RequestOptions().centerCrop()

        holder.binding.also {
            glide.load(IMAGE_PATH_PREFIX + listItem[position].backdropPath)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .apply(options)
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .into(it.movieImage)
            it.titleText.text = listItem[position].title
            it.ratingBar.rating = (listItem[position].voteAverage!!.div(2)).toFloat()

            //TODO: Change the date format to dd/mmm/yyyy from dd-mm-yy
            it.releaseDate.text = listItem[position].releaseDate
            it.voteCount.text = "(${listItem[position].voteCount})"
            it.genreText.text = ""
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}