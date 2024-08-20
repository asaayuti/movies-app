package com.example.moviesapp.detail

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.core.domain.model.Movie
import com.example.core.utils.loadImage
import com.example.moviesapp.R
import com.example.moviesapp.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        initBlurView()
        detailViewModel.getDetailMovie(movieId).observe(this) {
            showDetailMovie(it)
        }

    }

    private fun showDetailMovie(detailMovie: Movie?) {
        detailMovie?.let {
            binding.tvTitle.text = detailMovie.title
            binding.tvOverview.text = detailMovie.overview
            binding.ivFilm.loadImage(detailMovie.posterPath)

            var statusFavorite = detailMovie.isFavorite
            setStatusFavorite(statusFavorite)
            binding.ivFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                detailViewModel.setFavoriteMovie(detailMovie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        val icon =
            if (statusFavorite) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24
        binding.ivFavorite.setImageDrawable(
            ContextCompat.getDrawable(this, icon)
        )
    }

    private fun initBlurView() {
        val decorView = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowsBackground = decorView.background

        binding.blurView.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowsBackground)
            .setBlurRadius(10f)
        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}