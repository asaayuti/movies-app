package com.example.core.data.source

import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.domain.model.Movie
import com.example.core.utils.AppExecutors
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class MovieRepositoryTest {

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var appExecutors: AppExecutors

    private lateinit var movieRepository: MovieRepository

    private val movieEntity = MovieEntity(
        id = 1,
        overview = "Overview",
        title = "Title",
        posterPath = "/path.jpg",
        releaseDate = "2023-01-01",
        isFavorite = false
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
        movieRepository = MovieRepository(remoteDataSource, localDataSource, appExecutors)
    }

    @Test
    fun getFavoriteMovie() {
        `when`(localDataSource.getFavoriteMovie()).thenReturn(Flowable.just(listOf(movieEntity)))

        val testObserver = movieRepository.getFavoriteMovie().test()
        testObserver.assertValueCount(1)
        val result = testObserver.values()[0]
        assertEquals(1, result.size)
        assertEquals(movieEntity.id, result[0].id)
    }

    @Test
    fun getMovieDetail() {
        `when`(localDataSource.getMovieDetail(1)).thenReturn(Flowable.just(movieEntity))

        val testObserver = movieRepository.getMovieDetail(1).test()
        testObserver.assertValueCount(1)
        val result = testObserver.values()[0]
        assertEquals(movieEntity.id, result.id)
        assertEquals(movieEntity.title, result.title)
    }

    @Test
    fun setFavoriteMovie() {
        val synchronousExecutor = Executor { command -> command.run() }
        `when`(appExecutors.diskIO()).thenReturn(synchronousExecutor)

        val movie = Movie(
            id = 1,
            overview = "Overview",
            title = "Title",
            posterPath = "/path.jpg",
            releaseDate = "2023-01-01",
            isFavorite = false
        )

        movieRepository.setFavoriteMovie(movie, true)
        verify(localDataSource).setFavoriteMovie(movieEntity, true)
    }

    @Test
    fun getAllMovie() {
        `when`(localDataSource.getAllMovie()).thenReturn(Flowable.just(listOf(movieEntity)))

        val testObserver = movieRepository.getAllMovie(1).test()
        testObserver.assertValueCount(1)
        val result = testObserver.values()[0]
        assert(result is Resource.Success)
        assertEquals(1, result.data?.size)
        assertEquals(movieEntity.id, result.data?.get(0)?.id)
    }
}
