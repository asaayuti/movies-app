package com.example.moviesapp.core.data.source.local

import androidx.lifecycle.LiveData
import com.example.moviesapp.core.data.source.local.entity.MovieEntity
import com.example.moviesapp.core.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val movieDao: MovieDao) {

    fun getAllMovie(): LiveData<List<MovieEntity>> = movieDao.getAllMovie()

    fun getFavoriteMovie(): LiveData<List<MovieEntity>> = movieDao.getFavoriteMovie()

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