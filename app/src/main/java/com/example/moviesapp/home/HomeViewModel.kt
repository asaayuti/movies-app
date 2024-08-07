package com.example.moviesapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.moviesapp.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {

    val movies = movieUseCase.getAllMovie().toLiveData()
}