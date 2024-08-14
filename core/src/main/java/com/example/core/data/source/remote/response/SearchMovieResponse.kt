package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val listMovie: List<MovieResponse>,

    @field:SerializedName("total_results")
    val totalResults: Int
)