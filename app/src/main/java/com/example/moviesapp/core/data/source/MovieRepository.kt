package com.example.moviesapp.core.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.moviesapp.core.data.source.local.LocalDataSource
import com.example.moviesapp.core.data.source.remote.RemoteDataSource
import com.example.moviesapp.core.data.source.remote.network.ApiResponse
import com.example.moviesapp.core.data.source.remote.response.MovieResponse
import com.example.moviesapp.core.domain.model.Movie
import com.example.moviesapp.core.domain.repository.IMovieRepository
import com.example.moviesapp.core.utils.AppExecutors
import com.example.moviesapp.core.utils.DataMapper

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(): LiveData<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Movie>> {
                return (localDataSource.getAllMovie()).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getAllMovie()
            }

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovies(movieList)
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data.isNullOrEmpty()
            }

        }.asLiveData()

    override fun getFavoriteMovie(): LiveData<List<Movie>> {
        return (localDataSource.getFavoriteMovie()).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localDataSource, appExecutors)
            }
    }

}