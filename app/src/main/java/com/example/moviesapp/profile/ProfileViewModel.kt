package com.example.moviesapp.profile

import androidx.lifecycle.ViewModel
import com.example.moviesapp.core.domain.usecase.MovieUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel()