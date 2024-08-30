package com.example.moviedb.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val voteAverage: Double,
    val releaseDate: String,
    val posterPath: String,
)
