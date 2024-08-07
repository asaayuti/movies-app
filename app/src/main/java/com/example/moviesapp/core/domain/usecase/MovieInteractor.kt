package com.example.moviesapp.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.moviesapp.core.data.source.Resource
import com.example.moviesapp.core.domain.model.Movie
import com.example.moviesapp.core.domain.repository.IMovieRepository

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {
    override fun getAllMovie(): LiveData<Resource<List<Movie>>> = movieRepository.getAllMovie()

    override fun getFavoriteMovie(): LiveData<List<Movie>> = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)

}