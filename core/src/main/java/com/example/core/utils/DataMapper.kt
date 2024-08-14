package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Movie


object DataMapper {

    fun mapResponseToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath ?: "",
                releaseDate = it.releaseDate,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        overview = input.overview,
        title = input.title,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        isFavorite = input.isFavorite
    )

    fun mapEntityToDomain(input: MovieEntity) = Movie(
        id = input.id,
        overview = input.overview,
        title = input.title,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        isFavorite = input.isFavorite
    )


}