package com.example.moviesapp.detail

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.moviesapp.MyApplication
import com.example.moviesapp.R
import com.example.moviesapp.core.domain.model.Movie
import com.example.moviesapp.core.ui.ViewModelFactory
import com.example.moviesapp.databinding.ActivityDetailBinding
import eightbitlab.com.blurview.RenderScriptBlur
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val detailViewModel: DetailViewModel by viewModels { factory }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }

        val detailMovie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_DATA, Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }
        showDetailMovie(detailMovie)
        initBlurView()
    }

    private fun showDetailMovie(detailMovie: Movie?) {
        detailMovie?.let {
            binding.tvTitle.text = detailMovie.title
            binding.tvOverview.text = detailMovie.overview

            val posterUrl = "https://image.tmdb.org/t/p/w500" + detailMovie.posterPath
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transform(RoundedCorners(30))

            Glide.with(this@DetailActivity)
                .load(posterUrl)
                .apply(requestOptions)
                .into(binding.ivFilm)

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
        if (statusFavorite) {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.baseline_bookmark_24
                )
            )
        } else {
            binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.baseline_bookmark_border_24
                )
            )
        }
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
        const val EXTRA_DATA = "extra_data"
    }
}