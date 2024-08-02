package com.example.moviesapp.core.utils

import com.example.moviesapp.core.data.source.local.entity.MovieEntity
import com.example.moviesapp.core.data.source.remote.response.MovieResponse

object DataMapper {
    fun mapResponseToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                overview = it.overview,
                title = it.title,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }
}