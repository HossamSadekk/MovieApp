package com.example.moviedb.core.mapper

import com.example.moviedb.core.local.entity.MovieEntity
import com.example.moviedb.data.model.Movie

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
    )
}