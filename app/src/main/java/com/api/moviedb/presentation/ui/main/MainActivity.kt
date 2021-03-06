package com.api.moviedb.presentation.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.genere.GeneresResponse
import com.api.moviedb.databinding.ActivityMainBinding
import com.api.moviedb.presentation.ui.favmovie.FavMovieListActivity
import com.api.moviedb.presentation.ui.search.SearchMovieListActivity
import com.api.moviedb.util.ConstData.Companion.genreMap
import com.api.moviedb.util.GENRE_PREFERENCE_DATA
import com.api.moviedb.util.SEARCH_QUERY_INTENT_EXTRA
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var preferences: SharedPreferences

    @Inject
    lateinit var gson: Gson

    private val viewModel by viewModels<MainActivityViewModel> {
        defaultViewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preferences.contains(GENRE_PREFERENCE_DATA)) {
            val data = preferences.getString(GENRE_PREFERENCE_DATA, "")
            val response = gson.fromJson(data, GeneresResponse::class.java)
            processGenre(response)
        } else {
            viewModel.genreState.observe(this) {
                processGenre(it)
                val data = gson.toJson(it)
                Log.d("TAG", "gson data: $data")
                preferences.edit().putString(GENRE_PREFERENCE_DATA, data).apply()
            }
            viewModel.getGenre()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bookmark.setOnClickListener {
            val intent = Intent(this, FavMovieListActivity::class.java)
            startActivity(intent)
        }

        binding.searchSv.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    val intent = Intent(this@MainActivity, SearchMovieListActivity::class.java)
                    intent.putExtra(SEARCH_QUERY_INTENT_EXTRA, query)
                    startActivity(intent)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_now_playing,
                R.id.navigation_popular,
                R.id.navigation_top_rated,
                R.id.navigation_upcoming
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun processGenre(it: GeneresResponse) {
        it.genres.forEach { genre ->
            genreMap[genre.id] = genre.name
        }
    }
}