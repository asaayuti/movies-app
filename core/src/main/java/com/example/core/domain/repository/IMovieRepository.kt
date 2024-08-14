package com.example.core.domain.repository

import com.example.core.data.source.Resource
import com.example.core.domain.model.Movie
import io.reactivex.Flowable

interface IMovieRepository {

    fun getAllMovie(): Flowable<Resource<List<Movie>>>

    fun getFavoriteMovie(): Flowable<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)

    fun getMovieDetail(movieId: Int): Flowable<Movie>
}