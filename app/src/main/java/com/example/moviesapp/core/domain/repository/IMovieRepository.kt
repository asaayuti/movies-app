package com.example.moviesapp.core.domain.repository

import androidx.lifecycle.LiveData
import com.example.moviesapp.core.data.source.Resource
import com.example.moviesapp.core.domain.model.Movie

interface IMovieRepository {

    fun getAllMovie(): LiveData<Resource<List<Movie>>>

    fun getFavoriteMovie(): LiveData<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)
}