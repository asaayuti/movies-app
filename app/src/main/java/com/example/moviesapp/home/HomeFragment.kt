package com.example.moviesapp.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.data.source.Resource
import com.example.core.ui.MovieListAdapter
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.detail.DetailActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var binding: FragmentHomeBinding? = null
    private val compositeDisposable = CompositeDisposable()

    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            movieListAdapter = MovieListAdapter { movieId ->
                Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
                    startActivity(this)
                }
            }

            getMovies()

            binding?.rvMovie?.apply {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = movieListAdapter
            }
        }

    }

    private fun getMovies() {
        val searchStream = binding?.etSearch?.let {
            RxTextView.textChanges(it)
                .map { binding?.etSearch?.text.toString() }
                .subscribe { text ->
                    if (text.isNotEmpty()) {
                        getSearchMovies(text)
                    } else {
                        getPopularMovies()
                    }
                }
        }
        if (searchStream != null) {
            compositeDisposable.add(searchStream)
        }
    }

    private fun getSearchMovies(text: String) {
        homeViewModel.getSearchMovie(text).observe(viewLifecycleOwner) { movies ->
            when (movies) {
                is Resource.Loading -> binding?.pbMovie?.visibility = View.VISIBLE

                is Resource.Success -> {
                    binding?.pbMovie?.visibility = View.GONE
                    movieListAdapter.submitList(movies.data)
                }

                is Resource.Error -> {
                    binding?.apply {
                        pbMovie.visibility = View.GONE
                        viewError.root.visibility = View.VISIBLE
                        viewError.tvError.text =
                            movies.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
    }

    private fun getPopularMovies() {
        homeViewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> binding?.pbMovie?.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding?.pbMovie?.visibility = View.GONE
                        movieListAdapter.submitList(movies.data)
                    }

                    is Resource.Error -> {
                        binding?.apply {
                            pbMovie.visibility = View.GONE
                            viewError.root.visibility = View.VISIBLE
                            viewError.tvError.text =
                                movies.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvMovie?.adapter = null
        binding = null
        compositeDisposable.clear()
    }
}