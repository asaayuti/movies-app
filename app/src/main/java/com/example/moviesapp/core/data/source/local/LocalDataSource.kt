package com.example.moviesapp.core.data.source.local

import com.example.moviesapp.core.data.source.local.entity.MovieEntity
import com.example.moviesapp.core.data.source.local.room.MovieDao
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {

    fun getAllMovie(): Flowable<List<MovieEntity>> = movieDao.getAllMovie()

    fun getFavoriteMovie(): Flowable<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun insertMovies(movieList: List<MovieEntity>) = movieDao.insertMovie(movieList)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }

}