package com.example.moviesapp.core.data.source

import androidx.lifecycle.LiveData
import com.example.moviesapp.core.data.source.local.LocalDataSource
import com.example.moviesapp.core.data.source.local.entity.MovieEntity
import com.example.moviesapp.core.data.source.remote.RemoteDataSource
import com.example.moviesapp.core.data.source.remote.network.ApiResponse
import com.example.moviesapp.core.data.source.remote.response.MovieResponse
import com.example.moviesapp.core.utils.AppExecutors
import com.example.moviesapp.core.utils.DataMapper

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) {

    fun getAllMovie(): LiveData<Resource<List<MovieEntity>>> =
        object : NetworkBoundResource<List<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<MovieEntity>> {
                return localDataSource.getAllMovie()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getAllMovie()
            }

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertMovies(movieList)
            }

            override fun shouldFetch(data: List<MovieEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

        }.asLiveData()

    fun getFavoriteMovie(): LiveData<List<MovieEntity>> {
        return localDataSource.getFavoriteMovie()
    }

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
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