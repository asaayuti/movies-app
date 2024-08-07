package com.example.moviesapp.detail

import androidx.lifecycle.ViewModel
import com.example.moviesapp.core.domain.model.Movie
import com.example.moviesapp.core.domain.usecase.MovieUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) =
        movieUseCase.setFavoriteMovie(movie, newStatus)
}