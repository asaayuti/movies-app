package com.example.moviesapp.favorite

import androidx.lifecycle.ViewModel
import com.example.moviesapp.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val favoriteMovies = movieUseCase.getFavoriteMovie()
}