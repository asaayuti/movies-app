package com.example.core.data.source

import android.annotation.SuppressLint
import android.util.Log
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.domain.model.Movie
import com.example.core.domain.repository.IMovieRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SuppressLint("CheckResult")
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(page: Int): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flowable<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = page > 1 || data.isNullOrEmpty()

            override fun createCall(): Flowable<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getAllMovie(page)
            }

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovies(movieList)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                    }, { error ->
                        Log.e("SaveCallResult", "Error inserting movies: $error")
                    })
            }
        }.asFlowable()

    override fun getSearchMovie(query: String, page: Int): Flowable<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flowable<List<Movie>> =
                localDataSource.getSearchMovie(query).map { DataMapper.mapEntitiesToDomain(it) }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override fun createCall(): Flowable<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getSearchMovie(query, page)

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovies(movieList)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                    }, { error ->
                        Log.e("SaveCallResult", "Error inserting movies: $error")
                    })
            }
        }.asFlowable()

    override fun getFavoriteMovie(): Flowable<List<Movie>> {
        return localDataSource.getFavoriteMovie().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

    override fun getMovieDetail(movieId: Int): Flowable<Movie> =
        localDataSource.getMovieDetail(movieId).map { DataMapper.mapEntityToDomain(it) }
}
