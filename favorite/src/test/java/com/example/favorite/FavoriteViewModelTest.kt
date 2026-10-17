package com.example.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class FavoriteViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieUseCase: MovieUseCase

    private lateinit var favoriteViewModel: FavoriteViewModel

    private val dummyMovie = Movie(
        id = 1,
        overview = "Overview",
        title = "Title",
        posterPath = "/path.jpg",
        releaseDate = "2023-01-01",
        isFavorite = true
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(movieUseCase.getFavoriteMovie()).thenReturn(Flowable.just(listOf(dummyMovie)))
        favoriteViewModel = FavoriteViewModel(movieUseCase)
    }

    @Test
    fun testGetFavoriteMovies() {
        val observer = androidx.lifecycle.Observer<List<Movie>> {}
        try {
            favoriteViewModel.favoriteMovies.observeForever(observer)
            val value = favoriteViewModel.favoriteMovies.value
            assertEquals(1, value?.size)
            assertEquals(dummyMovie.id, value?.get(0)?.id)
        } finally {
            favoriteViewModel.favoriteMovies.removeObserver(observer)
        }
    }
}
