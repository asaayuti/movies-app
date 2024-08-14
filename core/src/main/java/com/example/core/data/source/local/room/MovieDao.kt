package com.example.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.source.local.entity.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieentity")
    fun getAllMovie(): Flowable<List<MovieEntity>>

    @Query("SELECT * FROM movieentity where isFavorite = 1")
    fun getFavoriteMovie(): Flowable<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: List<MovieEntity>): Completable

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)

    @Query("SELECT * FROM movieentity WHERE id = :movieId")
    fun getMovieDetail(movieId: Int): Flowable<MovieEntity>
}