package com.example.moviesapp.favorite

import androidx.lifecycle.ViewModel
import com.example.moviesapp.core.data.source.MovieRepository

class FavoriteViewModel(movieRepository: MovieRepository) : ViewModel() {

    val favoriteMovies = movieRepository.getFavoriteMovie()
}