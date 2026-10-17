package com.example.moviesapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.example.core.data.source.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.processors.BehaviorProcessor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val queryProcessor = BehaviorProcessor.createDefault("")

    val moviesResult: LiveData<Resource<List<Movie>>> = queryProcessor
        .debounce(300, TimeUnit.MILLISECONDS)
        .distinctUntilChanged()
        .switchMap { query ->
            if (query.trim().isEmpty()) {
                movieUseCase.getAllMovie()
            } else {
                movieUseCase.getSearchMovie(query.trim())
            }
        }
        .toLiveData()

    fun setSearchQuery(query: String) {
        queryProcessor.onNext(query)
    }
}
