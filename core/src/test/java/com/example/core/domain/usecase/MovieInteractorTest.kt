package com.example.core.domain.usecase

import com.example.core.domain.repository.IMovieRepository
import com.example.core.domain.model.Movie
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class MovieInteractorTest {

    @Mock
    private lateinit var movieRepository: IMovieRepository

    private lateinit var movieInteractor: MovieInteractor

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
        movieInteractor = MovieInteractor(movieRepository)
    }

    @Test
    fun testGetFavoriteMovie() {
        val dummyList = listOf(dummyMovie)
        `when`(movieRepository.getFavoriteMovie()).thenReturn(Flowable.just(dummyList))

        val testObserver = movieInteractor.getFavoriteMovie().test()
        testObserver.assertValue(dummyList)
        verify(movieRepository).getFavoriteMovie()
    }

    @Test
    fun testSetFavoriteMovie() {
        movieInteractor.setFavoriteMovie(dummyMovie, true)
        verify(movieRepository).setFavoriteMovie(dummyMovie, true)
    }

    @Test
    fun testGetDetailMovie() {
        `when`(movieRepository.getMovieDetail(1)).thenReturn(Flowable.just(dummyMovie))

        val testObserver = movieInteractor.getDetailMovie(1).test()
        testObserver.assertValue(dummyMovie)
        verify(movieRepository).getMovieDetail(1)
    }
}
