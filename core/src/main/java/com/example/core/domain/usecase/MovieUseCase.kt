package com.example.core.domain.usecase

import com.example.core.data.source.Resource
import com.example.core.domain.model.Movie
import io.reactivex.Flowable

interface MovieUseCase {
    fun getAllMovie(): Flowable<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flowable<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)

}