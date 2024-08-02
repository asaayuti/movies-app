package com.example.moviesapp.core.utils

import android.content.Context
import com.example.moviesapp.R
import com.example.moviesapp.core.data.source.remote.response.MovieResponse
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(R.raw.movies).bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun loadMovies(): List<MovieResponse> {
        val list = ArrayList<MovieResponse>()
        val responseObject = JSONObject(parsingFileToString().toString())
        val listArray = responseObject.getJSONArray("results")
        for (i in 0 until listArray.length()) {
            val movie = listArray.getJSONObject(i)

            val id = movie.getInt("id")
            val overview = movie.getString("overview")
            val title = movie.getString("title")
            val posterPath = movie.getString("poster_path")
            val releaseDate = movie.getString("release_date")

            val movieResponse = MovieResponse(
                id = id,
                overview = overview,
                title = title,
                posterPath = posterPath,
                releaseDate = releaseDate
            )
            list.add(movieResponse)
        }
        return list
    }

}