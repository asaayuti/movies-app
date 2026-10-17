package com.example.moviesapp.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import io.reactivex.Flowable
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieUseCase: MovieUseCase

    private lateinit var detailViewModel: DetailViewModel

    private val dummyMovie = Movie(
        id = 1,
        overview = "Test Overview",
        title = "Test Movie",
        posterPath = "/poster.jpg",
        releaseDate = "2023-01-01",
        isFavorite = false
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        detailViewModel = DetailViewModel(movieUseCase)
    }

    @Test
    fun testSetFavoriteMovie() {
        detailViewModel.setFavoriteMovie(dummyMovie, true)
        verify(movieUseCase).setFavoriteMovie(dummyMovie, true)
    }

    @Test
    fun testGetDetailMovie() {
        `when`(movieUseCase.getDetailMovie(1)).thenReturn(Flowable.just(dummyMovie))
        
        val result = detailViewModel.getDetailMovie(1)
        assertNotNull(result)
    }
}
