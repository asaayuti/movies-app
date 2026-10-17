package com.example.moviesapp.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

            binding?.rvMovie?.apply {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = movieListAdapter

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (dy > 0 && !recyclerView.canScrollVertically(1)) {
                            homeViewModel.loadNextPage()
                        }
                    }
                })
            }

            observeMovies()
            setupSearchStream()

            binding?.etSearch?.let { editText ->
                ViewCompat.setOnApplyWindowInsetsListener(editText) { v, insets ->
                    val statusBarInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
                    val defaultMargin = (16 * v.resources.displayMetrics.density).toInt()
                    v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        topMargin = statusBarInset + defaultMargin
                    }
                    insets
                }
            }
        }
    }

    private fun observeMovies() {
        homeViewModel.moviesResult.observe(viewLifecycleOwner) { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                        if (movies.data.isNullOrEmpty()) {
                            binding?.apply {
                                pbMovie.visibility = View.VISIBLE
                                viewError.root.visibility = View.GONE
                            }
                        }
                    }

                    is Resource.Success -> {
                        binding?.apply {
                            pbMovie.visibility = View.GONE
                            viewError.root.visibility = View.GONE
                        }
                        movieListAdapter.submitList(movies.data)
                    }

                    is Resource.Error -> {
                        binding?.pbMovie?.visibility = View.GONE
                        if (movies.data.isNullOrEmpty()) {
                            binding?.apply {
                                viewError.root.visibility = View.VISIBLE
                                viewError.tvError.text =
                                    movies.message ?: getString(R.string.something_wrong)
                            }
                        } else {
                            movieListAdapter.submitList(movies.data)
                        }
                    }
                }
            }
        }
    }

    private fun setupSearchStream() {
        binding?.etSearch?.let { editText ->
            val searchStream = RxTextView.textChanges(editText)
                .subscribe({ text ->
                    homeViewModel.setSearchQuery(text.toString())
                }, { error ->
                    android.util.Log.e("HomeFragment", "Search textChanges error: $error")
                })
            compositeDisposable.add(searchStream)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvMovie?.adapter = null
        binding = null
        compositeDisposable.clear()
    }
}
