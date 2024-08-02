package com.example.moviesapp.home

import androidx.lifecycle.ViewModel
import com.example.moviesapp.core.data.source.MovieRepository

class HomeViewModel(movieRepository: MovieRepository) : ViewModel() {

    val movies = movieRepository.getAllMovie()
}