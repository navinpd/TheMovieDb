package com.api.moviedb.presentation.ui.main.nowplaying

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.databinding.FragmentHomeBinding
import com.api.moviedb.presentation.ui.moviedetail.MovieDetailActivity
import com.api.moviedb.util.INextPage
import com.api.moviedb.util.MOVIE_ID_INTENT_EXTRA
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NowPlayingFragment : Fragment(), INextPage {

    @Inject
    lateinit var glide: RequestManager

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val nowPlayingList = mutableListOf<Results>()
    private val viewModel by viewModels<NowPlayingViewModel> {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nowPlayingRV = binding.nowPlayingRv
        val nowPlayingAdapter = MovieListAdapter(nowPlayingList, glide)

        nowPlayingAdapter.requestForNextItem = this
        nowPlayingRV.adapter = nowPlayingAdapter
        nowPlayingRV.layoutManager = LinearLayoutManager(activity)

        viewModel.nowPlayingMoviesMovieData.observe(viewLifecycleOwner) {
            val size = nowPlayingList.size
            nowPlayingList.addAll(it.results)
            nowPlayingAdapter.notifyItemRangeChanged(size, size + it.results.size)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (nowPlayingList.size == 0) {
            viewModel.getNowPlayingMovies(1)
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