package com.example.moviesapp.core.data.source.local

import com.example.moviesapp.core.data.source.local.entity.MovieEntity
import com.example.moviesapp.core.data.source.local.room.MovieDao
import io.reactivex.Flowable

class LocalDataSource private constructor(private val movieDao: MovieDao) {

    fun getAllMovie(): Flowable<List<MovieEntity>> = movieDao.getAllMovie()

    fun getFavoriteMovie(): Flowable<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun insertMovies(movieList: List<MovieEntity>) = movieDao.insertMovie(movieList)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(movieDao)
            }
    }
}