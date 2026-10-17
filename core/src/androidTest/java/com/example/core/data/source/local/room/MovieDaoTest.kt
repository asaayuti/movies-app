package com.example.core.data.source.local.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.data.source.local.entity.MovieEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    private lateinit var database: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun initDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        movieDao = database.movieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetMovies() {
        val movieEntity = MovieEntity(
            id = 1,
            overview = "Overview",
            title = "Test Movie",
            posterPath = "/path.jpg",
            releaseDate = "2023-01-01",
            isFavorite = true
        )

        movieDao.insertMovie(listOf(movieEntity)).blockingAwait()

        val list = movieDao.getAllMovie().blockingFirst()
        org.junit.Assert.assertEquals(1, list.size)
        org.junit.Assert.assertEquals(1, list[0].id)
        org.junit.Assert.assertEquals("Test Movie", list[0].title)
    }

    @Test
    fun insertAndGetFavoriteMovies() {
        val movieEntity = MovieEntity(
            id = 1,
            overview = "Overview",
            title = "Test Movie",
            posterPath = "/path.jpg",
            releaseDate = "2023-01-01",
            isFavorite = true
        )

        movieDao.insertMovie(listOf(movieEntity)).blockingAwait()

        val list = movieDao.getFavoriteMovie().blockingFirst()
        org.junit.Assert.assertEquals(1, list.size)
        org.junit.Assert.assertEquals(1, list[0].id)
        org.junit.Assert.assertEquals(true, list[0].isFavorite)
    }
}
