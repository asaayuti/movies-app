package com.example.moviesapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.data.source.Resource
import com.example.core.ui.MovieAdapter
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.main_color)

            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { movieId ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
                startActivity(intent)
            }

            homeViewModel.movies.observe(viewLifecycleOwner) { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> binding.pbMovie.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.pbMovie.visibility = View.GONE
                            movieAdapter.setData(movies.data)
                        }

                        is Resource.Error -> {
                            binding.pbMovie.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                movies.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }

            binding.etSearch.setOnEditorActionListener { v, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val text = binding.etSearch.text.toString()
                    homeViewModel.getSearchMovie(text).observe(viewLifecycleOwner) { movies ->
                        if (movies != null) {
                            when (movies) {
                                is Resource.Loading -> binding.pbMovie.visibility = View.VISIBLE

                                is Resource.Success -> {
                                    binding.pbMovie.visibility = View.GONE
                                    movieAdapter.setData(movies.data)
                                    binding.etSearch.text.clear()
                                    hideKeyboard(v)
                                }

                                is Resource.Error -> {
                                    binding.pbMovie.visibility = View.GONE
                                    binding.viewError.root.visibility = View.VISIBLE
                                    binding.viewError.tvError.text =
                                        movies.message ?: getString(R.string.something_wrong)
                                }
                            }
                        }
                    }
                    true
                } else {
                    false
                }
            }

            with(binding.rvMovie) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}