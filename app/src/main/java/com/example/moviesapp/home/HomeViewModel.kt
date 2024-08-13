package com.example.moviesapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {

    val movies = movieUseCase.getAllMovie().toLiveData()
}