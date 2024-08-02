package com.example.moviesapp.detail

import androidx.lifecycle.ViewModel
import com.example.moviesapp.core.data.source.MovieRepository
import com.example.moviesapp.core.data.source.local.entity.MovieEntity

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun setFavoriteMovie(movie: MovieEntity, newStatus: Boolean) =
        movieRepository.setFavoriteMovie(movie, newStatus)
}