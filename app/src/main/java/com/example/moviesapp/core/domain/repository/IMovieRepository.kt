package com.example.moviesapp.core.domain.repository

import com.example.moviesapp.core.data.source.Resource
import com.example.moviesapp.core.domain.model.Movie
import io.reactivex.Flowable

interface IMovieRepository {

    fun getAllMovie(): Flowable<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flowable<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)
}