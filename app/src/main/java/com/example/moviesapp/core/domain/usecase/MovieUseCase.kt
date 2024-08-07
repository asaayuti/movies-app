package com.example.moviesapp.core.domain.usecase

import com.example.moviesapp.core.data.source.Resource
import com.example.moviesapp.core.domain.model.Movie
import io.reactivex.Flowable

interface MovieUseCase {
    fun getAllMovie(): Flowable<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flowable<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)

}