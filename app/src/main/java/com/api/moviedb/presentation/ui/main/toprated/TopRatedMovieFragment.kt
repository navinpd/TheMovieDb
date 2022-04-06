package com.api.moviedb.presentation.ui.main.toprated

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.databinding.FragmentNotificationsBinding
import com.api.moviedb.presentation.ui.main.nowplaying.MovieListAdapter
import com.api.moviedb.presentation.ui.moviedetail.MovieDetailActivity
import com.api.moviedb.util.INextPage
import com.api.moviedb.util.MOVIE_ID_INTENT_EXTRA
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TopRatedMovieFragment : Fragment(), INextPage {

    private var _binding: FragmentNotificationsBinding? = null

    private val topRatedMovieList = mutableListOf<Results>()
    private val viewModel by viewModels<TopRatedMovieViewModel> {
        defaultViewModelProviderFactory
    }

    @Inject
    lateinit var glide: RequestManager

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nowPlayingRV: RecyclerView = binding.topRatedRv

        val adapter = MovieListAdapter(topRatedMovieList, glide)
        adapter.requestForNextItem = this
        nowPlayingRV.adapter = adapter
        nowPlayingRV.layoutManager = LinearLayoutManager(activity)

        viewModel.topRatedMovieData.observe(viewLifecycleOwner) {
            val size = topRatedMovieList.size
            topRatedMovieList.addAll(it.results)
            adapter.notifyItemRangeChanged(size, size + it.results.size)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (topRatedMovieList.size == 0) {
            viewModel.getTopRatedMovies(page = 1)
        }
    }

    override fun loadNextPage() {
        viewModel.invokeNextPage()
    }

    override fun getMovieDetails(movieId: Int) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_ID_INTENT_EXTRA, movieId)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}