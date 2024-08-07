package com.example.moviesapp.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.moviesapp.core.data.source.Resource
import com.example.moviesapp.core.domain.model.Movie

interface MovieUseCase {
    fun getAllMovie(): LiveData<Resource<List<Movie>>>
    fun getFavoriteMovie(): LiveData<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)

}