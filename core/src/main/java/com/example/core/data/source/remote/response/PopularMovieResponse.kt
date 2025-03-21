package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("results")
    val results: List<MovieResponse>
)