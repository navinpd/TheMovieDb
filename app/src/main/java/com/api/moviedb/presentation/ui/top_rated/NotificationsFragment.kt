package com.api.moviedb.presentation.ui.top_rated

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.api.moviedb.databinding.FragmentNotificationsBinding
import com.api.moviedb.presentation.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    @Inject
    lateinit var viewModel : MovieViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        viewModel.movieDetailState.observe(viewLifecycleOwner) {
            Log.d("TAG", it.toString())
            textView.text = it.toString()
        }

        viewModel.getMovieDetails(550)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}