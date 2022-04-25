package com.api.moviedb.presentation.ui.main.nowplaying

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.moviedb.R
import com.api.moviedb.databinding.FragmentHomeBinding
import com.api.moviedb.presentation.ui.moviedetail.MovieDetailActivity
import com.api.moviedb.presentation.ui.viewmodel.ViewMovieState
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
        val nowPlayingAdapter = MovieListAdapter(glide)
        nowPlayingAdapter.submitList(emptyList())

        nowPlayingAdapter.requestForNextItem = this
        nowPlayingRV.adapter = nowPlayingAdapter
        nowPlayingRV.layoutManager = LinearLayoutManager(activity)

        var isOnCreate = true

        viewModel.nowPlayingMoviesMovieData.observe(viewLifecycleOwner) {
            nowPlayingAdapter.submitList(it)
            val pos = viewModel.position
            Log.d("TAG", "viewModel.position $pos")

            if(pos > 0 && isOnCreate) {
                Log.d("TAG", "Under isOnCreate $pos")
                isOnCreate = !isOnCreate
                (nowPlayingRV.layoutManager as LinearLayoutManager)
                    .smoothScrollToPosition(nowPlayingRV, null, pos)
                viewModel.position = 0
            }
        }

        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it) {
                is ViewMovieState.ShowError -> {
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = it.message
                }

                is ViewMovieState.HideLoading -> {
                    binding.progressBar.visibility = View.GONE
                }

                is ViewMovieState.ShowLoading -> {
                    binding.errorMessage.text = getString(R.string.no_data_found)
                    binding.errorMessage.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

//        viewModel.getNowPlayingMovies(1)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNowPlayingMovies(1)
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
        Log.e("TAG", "the view is destroyed")
        _binding = null
    }
}