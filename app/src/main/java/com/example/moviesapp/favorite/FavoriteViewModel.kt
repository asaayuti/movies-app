package com.example.moviesapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.moviesapp.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {

    val favoriteMovies = movieUseCase.getFavoriteMovie().toLiveData()
}