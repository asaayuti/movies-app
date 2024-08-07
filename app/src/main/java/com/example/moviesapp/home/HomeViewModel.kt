package com.example.moviesapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moviesapp.core.domain.usecase.MovieUseCase

class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val movies = movieUseCase.getAllMovie().asLiveData()
}