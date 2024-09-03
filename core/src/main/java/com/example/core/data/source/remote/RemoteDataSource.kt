package com.example.core.data.source.remote

import android.annotation.SuppressLint
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.MovieResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@SuppressLint("CheckResult")
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getAllMovie(): Flowable<ApiResponse<List<MovieResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<MovieResponse>>>()

        val client = apiService.getPopularMovies()

        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.results
                resultData.onNext(
                    if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray)
                    else ApiResponse.Empty
                )
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun getSearchMovie(query: String): Flowable<ApiResponse<List<MovieResponse>>> {
        val resultData = PublishSubject.create<ApiResponse<List<MovieResponse>>>()

        val client = apiService.getSearchMovie(query)

        client
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response.listMovie
                resultData.onNext(
                    if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray)
                    else ApiResponse.Empty
                )
            }, { error ->
                resultData.onNext(ApiResponse.Error(error.message.toString()))
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}