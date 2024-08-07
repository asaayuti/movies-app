package com.example.moviesapp.core.di

import android.content.Context
import com.example.moviesapp.core.data.source.MovieRepository
import com.example.moviesapp.core.data.source.local.LocalDataSource
import com.example.moviesapp.core.data.source.local.room.MovieDatabase
import com.example.moviesapp.core.data.source.remote.RemoteDataSource
import com.example.moviesapp.core.domain.repository.IMovieRepository
import com.example.moviesapp.core.domain.usecase.MovieInteractor
import com.example.moviesapp.core.domain.usecase.MovieUseCase
import com.example.moviesapp.core.utils.AppExecutors
import com.example.moviesapp.core.utils.JsonHelper

object Injection {

    private fun provideRepository(context: Context): IMovieRepository {
        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideMovieUseCase(context: Context): MovieUseCase {
        val repository = provideRepository(context)
        return MovieInteractor(repository)
    }
}