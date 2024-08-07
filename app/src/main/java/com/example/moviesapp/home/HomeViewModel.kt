package com.example.moviesapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.moviesapp.core.domain.usecase.MovieUseCase

class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val movies = movieUseCase.getAllMovie().toLiveData()
}