package com.example.core.domain.usecase

import com.example.core.data.source.Resource
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.IMovieRepository
import io.reactivex.Flowable
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository) :
    MovieUseCase {
    override fun getAllMovie(): Flowable<Resource<List<Movie>>> = movieRepository.getAllMovie()

    override fun getSearchMovie(query: String): Flowable<Resource<List<Movie>>> =
        movieRepository.getSearchMovie(query)

    override fun getFavoriteMovie(): Flowable<List<Movie>> = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)

    override fun getDetailMovie(movieId: Int): Flowable<Movie> =
        movieRepository.getMovieDetail(movieId)
}