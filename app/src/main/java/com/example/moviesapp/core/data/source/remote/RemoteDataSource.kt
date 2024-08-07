package com.example.moviesapp.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.BuildConfig
import com.example.moviesapp.core.data.source.remote.network.ApiResponse
import com.example.moviesapp.core.data.source.remote.network.ApiService
import com.example.moviesapp.core.data.source.remote.response.ListMovieResponse
import com.example.moviesapp.core.data.source.remote.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {

    fun getAllMovie(): LiveData<ApiResponse<List<MovieResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<MovieResponse>>>()

        val client = apiService.getPopularMovies(BuildConfig.API_KEY)

        client.enqueue(object : Callback<ListMovieResponse> {
            override fun onResponse(
                call: Call<ListMovieResponse>,
                response: Response<ListMovieResponse>
            ) {
                val dataArray = response.body()?.results
                resultData.value = if (dataArray != null) {
                    ApiResponse.Success(dataArray)
                } else {
                    ApiResponse.Empty
                }
            }

            override fun onFailure(call: Call<ListMovieResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", "onFailure: ${t.message}")
            }

        })

        return resultData
    }

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService)
            }
    }
}