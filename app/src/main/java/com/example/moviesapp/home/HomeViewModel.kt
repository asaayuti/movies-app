package com.example.moviesapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.data.source.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private data class SearchState(
        val query: String = "",
        val page: Int = 1
    )

    private val stateProcessor = BehaviorProcessor.createDefault(SearchState())
    private val compositeDisposable = CompositeDisposable()

    private val _moviesResult = MutableLiveData<Resource<List<Movie>>>()
    val moviesResult: LiveData<Resource<List<Movie>>> = _moviesResult

    private val accumulatedMovies = mutableListOf<Movie>()
    private var isPageLoading = false
    private var currentPage = 1
    private var currentQuery = ""

    init {
        val disposable = stateProcessor
            .debounce { state ->
                if (state.page == 1) Flowable.timer(300, TimeUnit.MILLISECONDS)
                else Flowable.timer(0, TimeUnit.MILLISECONDS)
            }
            .distinctUntilChanged()
            .switchMap { state ->
                val flowable = if (state.query.trim().isEmpty()) {
                    movieUseCase.getAllMovie(state.page)
                } else {
                    movieUseCase.getSearchMovie(state.query.trim(), state.page)
                }
                flowable.subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isPageLoading = true
                        if (currentPage == 1 && accumulatedMovies.isEmpty()) {
                            _moviesResult.value = Resource.Loading(null)
                        }
                    }

                    is Resource.Success -> {
                        isPageLoading = false
                        val newMovies = resource.data ?: emptyList()
                        if (currentPage == 1) {
                            accumulatedMovies.clear()
                        }
                        val existingIds = accumulatedMovies.map { it.id }.toSet()
                        val uniqueNewMovies = newMovies.filter { it.id !in existingIds }
                        accumulatedMovies.addAll(uniqueNewMovies)

                        _moviesResult.value = Resource.Success(ArrayList(accumulatedMovies))
                    }

                    is Resource.Error -> {
                        isPageLoading = false
                        _moviesResult.value = Resource.Error(resource.message ?: "Error", ArrayList(accumulatedMovies))
                    }
                }
            }, { error ->
                isPageLoading = false
                _moviesResult.value = Resource.Error(error.message ?: "Unknown error", ArrayList(accumulatedMovies))
            })

        compositeDisposable.add(disposable)
    }

    fun setSearchQuery(query: String) {
        if (query != currentQuery) {
            currentQuery = query
            currentPage = 1
            accumulatedMovies.clear()
            stateProcessor.onNext(SearchState(query = currentQuery, page = currentPage))
        }
    }

    fun loadNextPage() {
        if (!isPageLoading) {
            currentPage++
            stateProcessor.onNext(SearchState(query = currentQuery, page = currentPage))
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
