package com.example.moviesapp.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.data.source.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.usecase.MovieUseCase
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieUseCase: MovieUseCase

    private lateinit var homeViewModel: HomeViewModel

    private val dummyMovies = listOf(
        Movie(
            id = 1,
            overview = "Overview 1",
            title = "Movie 1",
            posterPath = "/path1.jpg",
            releaseDate = "2023-01-01",
            isFavorite = false
        )
    )

    companion object {
        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @JvmStatic
        @AfterClass
        fun tearDownClass() {
            RxJavaPlugins.reset()
            RxAndroidPlugins.reset()
        }
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(movieUseCase.getAllMovie(1)).thenReturn(Flowable.just(Resource.Success(dummyMovies)))
        homeViewModel = HomeViewModel(movieUseCase)
    }

    @Test
    fun testGetAllMoviesSuccess() {
        val observer = androidx.lifecycle.Observer<Resource<List<Movie>>> {}
        try {
            homeViewModel.moviesResult.observeForever(observer)
            val result = homeViewModel.moviesResult.value
            assert(result is Resource.Success)
            assertEquals(1, result?.data?.size)
            assertEquals("Movie 1", result?.data?.get(0)?.title)
        } finally {
            homeViewModel.moviesResult.removeObserver(observer)
        }
    }

    @Test
    fun testSetSearchQuery() {
        `when`(movieUseCase.getSearchMovie("Test", 1)).thenReturn(Flowable.just(Resource.Success(dummyMovies)))
        
        val observer = androidx.lifecycle.Observer<Resource<List<Movie>>> {}
        try {
            homeViewModel.moviesResult.observeForever(observer)
            homeViewModel.setSearchQuery("Test")
            val result = homeViewModel.moviesResult.value
            assert(result is Resource.Success)
        } finally {
            homeViewModel.moviesResult.removeObserver(observer)
        }
    }
}
