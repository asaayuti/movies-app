package com.example.moviesapp.home

import androidx.lifecycle.ViewModel
import com.example.moviesapp.core.domain.usecase.MovieUseCase

class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val movies = movieUseCase.getAllMovie()
}