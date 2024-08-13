package com.example.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val favoriteMovies = movieUseCase.getFavoriteMovie().toLiveData()
}