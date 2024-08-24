package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.PopularMovieResponse
import com.example.core.data.source.remote.response.SearchMovieResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(): Flowable<PopularMovieResponse>

    @GET("search/movie")
    fun getSearchMovie(@Query("query") query: String): Flowable<SearchMovieResponse>
}