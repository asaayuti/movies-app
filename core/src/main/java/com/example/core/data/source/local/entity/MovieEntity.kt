package com.example.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MovieEntity(
    @PrimaryKey
    var id: Int,
    var overview: String,
    var title: String,
    var posterPath: String,
    var releaseDate: String,
    var isFavorite: Boolean = false
) : Parcelable
