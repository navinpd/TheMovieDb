package com.api.moviedb.presentation.ui.toprated

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.api.moviedb.databinding.FragmentNotificationsBinding
import com.api.moviedb.presentation.ui.upcoming.UpcomingMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TopRatedMovieFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val viewModel by viewModels<TopRatedMovieViewModel> {
        defaultViewModelProviderFactory
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications

        viewModel.topRatedMovieData.observe(viewLifecycleOwner) {
            Log.d("TAG", it.toString())
            textView.text = it.toString()
        }

        viewModel.getTopRatedMovies(page = 1)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}