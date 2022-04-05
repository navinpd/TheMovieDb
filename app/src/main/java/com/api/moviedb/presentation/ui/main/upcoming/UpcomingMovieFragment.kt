package com.api.moviedb.presentation.ui.main.upcoming

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.moviedb.R
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.databinding.FragmentUpcomingBinding
import com.api.moviedb.presentation.ui.main.nowplaying.NowPlayingAdapter
import com.api.moviedb.presentation.ui.moviedetail.MovieDetailActivity
import com.api.moviedb.util.INextPage
import com.api.moviedb.util.MOVIE_ID_INTENT_EXTRA
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingMovieFragment : Fragment(), INextPage {

    @Inject
    lateinit var glide: RequestManager

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<UpcomingMovieViewModel> {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nowPlayingRV: RecyclerView = binding.upcomingRv

        val nowPlayingList = mutableListOf<Results>()
        val adapter = NowPlayingAdapter(nowPlayingList, glide)
        adapter.requestForNextItem = this
        nowPlayingRV.adapter = adapter
        nowPlayingRV.layoutManager = LinearLayoutManager(activity)

        viewModel.upcomingMoviesMovieData.observe(viewLifecycleOwner) {
            val size = nowPlayingList.size
            nowPlayingList.addAll(it.results)
            adapter.notifyItemRangeChanged(size, size + it.results.size)
        }

        viewModel.upcomingMovies(1)

        return root
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