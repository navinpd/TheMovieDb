package com.api.moviedb.presentation.ui.nowplaying

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.moviedb.data.remote.model.nowplaying.Results
import com.api.moviedb.databinding.FragmentHomeBinding
import com.api.moviedb.util.INextPage
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NowPlayingFragment : Fragment(), INextPage {

    @Inject
    lateinit var glide: RequestManager

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        val nowPlayingRV: RecyclerView = binding.nowPlayingRv

        val nowPlayingList = mutableListOf<Results>()
        val nowPlayingAdapter = NowPlayingAdapter(nowPlayingList, glide)
        nowPlayingAdapter.requestForNextItem = this
        nowPlayingRV.adapter = nowPlayingAdapter
        nowPlayingRV.layoutManager = LinearLayoutManager(activity)

        viewModel.nowPlayingMoviesMovieData.observe(viewLifecycleOwner) {
            val size = nowPlayingList.size
            nowPlayingList.addAll(it.results)
            nowPlayingAdapter.notifyItemRangeChanged(size, size + it.results.size)
        }

        if (nowPlayingList.size == 0) {
            viewModel.getNowPlayingMovies(1)
        }

        return root
    }

    override fun loadNextPage() {
        viewModel.invokeNextPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}