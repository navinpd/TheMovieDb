package com.api.moviedb.presentation.ui.nowplaying

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.databinding.MovieViewAdapterBinding
import com.api.moviedb.presentation.ui.MainActivity
import com.api.moviedb.util.IMAGE_PATH_PREFIX
import com.api.moviedb.util.INextPage
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.internal.managers.FragmentComponentManager

class NowPlayingAdapter(
    private val listItem: List<Results>,
    private val glide: RequestManager
) : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    lateinit var requestForNextItem: INextPage
    lateinit var context: Context

    inner class ViewHolder(val binding: MovieViewAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieViewAdapterBinding.inflate(layoutInflater, parent, false)
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

            //TODO: Change the date format to dd/mmm/yyyy from dd-mm-yy
            it.releaseDate.text = result.releaseDate
            it.voteCount.text = "(${result.voteCount})"
            var genre: String? = ""
            result.genreIds.forEach { genreId ->
                genre =
                    if (genre!!.isEmpty())
                        (FragmentComponentManager.findActivity(context) as MainActivity)
                            .genreMap[genreId]
                    else "$genre, " + (FragmentComponentManager.findActivity(context) as MainActivity)
                        .genreMap[genreId]
            }
            it.genreText.text = genre
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}