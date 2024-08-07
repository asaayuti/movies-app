package com.example.moviesapp.core.di

import com.example.moviesapp.core.data.source.MovieRepository
import com.example.moviesapp.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(movieRepository: MovieRepository): IMovieRepository

}