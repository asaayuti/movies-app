package com.example.moviesapp.di

import com.example.moviesapp.core.domain.usecase.MovieInteractor
import com.example.moviesapp.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}