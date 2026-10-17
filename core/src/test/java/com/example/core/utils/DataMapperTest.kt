package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Movie
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class DataMapperTest {

    private lateinit var movieResponse: MovieResponse
    private lateinit var movieEntity: MovieEntity
    private lateinit var movie: Movie

    @Before
    fun setUp() {
        movieResponse = mock(MovieResponse::class.java).apply {
            `when`(id).thenReturn(1)
            `when`(overview).thenReturn("Overview")
            `when`(title).thenReturn("Title")
            `when`(posterPath).thenReturn("/path.jpg")
            `when`(releaseDate).thenReturn("2023-01-01")
        }
        movieEntity = MovieEntity(
            id = 1,
            overview = "Overview",
            title = "Title",
            posterPath = "/path.jpg",
            releaseDate = "2023-01-01",
            isFavorite = false
        )
        movie = Movie(
            id = 1,
            overview = "Overview",
            title = "Title",
            posterPath = "/path.jpg",
            releaseDate = "2023-01-01",
            isFavorite = false
        )
    }

    @Test
    fun mapResponseToEntities() {
        val entityList = DataMapper.mapResponseToEntities(listOf(movieResponse))
        assertEquals(1, entityList.size)
        assertEquals(movieResponse.id, entityList[0].id)
        assertEquals(movieResponse.title, entityList[0].title)
    }

    @Test
    fun mapEntitiesToDomain() {
        val domainList = DataMapper.mapEntitiesToDomain(listOf(movieEntity))
        assertEquals(1, domainList.size)
        assertEquals(movieEntity.id, domainList[0].id)
        assertEquals(movieEntity.title, domainList[0].title)
    }

    @Test
    fun mapDomainToEntity() {
        val entity = DataMapper.mapDomainToEntity(movie)
        assertEquals(movie.id, entity.id)
        assertEquals(movie.title, entity.title)
        assertEquals(movie.isFavorite, entity.isFavorite)
    }

    @Test
    fun mapEntityToDomain() {
        val domain = DataMapper.mapEntityToDomain(movieEntity)
        assertEquals(movieEntity.id, domain.id)
        assertEquals(movieEntity.title, domain.title)
        assertEquals(movieEntity.isFavorite, domain.isFavorite)
    }
}
