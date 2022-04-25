package com.api.moviedb.util

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.movieDetails.Genres
import com.api.moviedb.data.remote.model.movieDetails.SpokenLanguages
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions

const val IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/original"
const val MOVIE_ID_INTENT_EXTRA = "movie_id"
const val SEARCH_QUERY_INTENT_EXTRA = "query"
const val FAV_MOVIE_INTENT_EXTRA = "favorite_movie"
const val GENRE_PREFERENCE_DATA = "GENRE"

class ConstData {
    companion object {
        var genreMap = HashMap<Int?, String?>()

        fun getGenreNames(genres: ArrayList<Genres>, context: Context): String {
            val builder = StringBuilder()

            genres.forEach { genreId ->
                builder.append("${genreId.name}, ")
            }
            return if (builder.toString().isEmpty())
                context.getString(R.string.genre_hyphen)
            else
                context.getString(R.string.genre_data, builder.toString())
        }

        fun loadImageToHolder(
            glide: RequestManager, imagePath: String,
            movieImage: AppCompatImageView,
        ) {
            val options = RequestOptions().centerInside()

            glide.load(imagePath)
                .error(R.drawable.ic_baseline_error_24)
                .apply(options)
                .placeholder(R.drawable.ic_baseline_downloading_24)
                .into(movieImage)
        }


        fun getGenreFromId(data: ArrayList<Int>, context: Context): String {
            var genre = ""
            data.forEach { genreId ->
                genre =
                    if (genre.isEmpty()) genreMap[genreId] ?: "" else "$genre, " + genreMap[genreId]
            }
            if (genre.isEmpty()) context.getString(R.string.genre_hyphen) else context.getString(
                R.string.genre_data,
                genre
            )
            return genre
        }

        fun getGenre(genres: ArrayList<Genres>, context: Context): String {
            var genre = ""
            genres.forEach { gen ->
                genre = if (genre.isEmpty()) gen.name!! else genre + ", " + gen.name
            }

            if (genre.isEmpty()) context.getString(R.string.genre_hyphen) else context.getString(
                R.string.genre_data,
                genre
            )
            return genre
        }

        fun spokenLanguage(it: ArrayList<SpokenLanguages>, context: Context): String {
            var lang = ""
            it.forEach { language ->
                lang =
                    if (lang.isEmpty()) language.englishName!! else "$lang, ${language.englishName}"
            }
            if (lang.isEmpty()) context.getString(R.string.language_hyphen) else context.getString(
                R.string.language_data,
                lang
            )
            return lang
        }

    }
}