package com.example.moviesapp.profile

import androidx.lifecycle.ViewModel
import com.example.moviesapp.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel()