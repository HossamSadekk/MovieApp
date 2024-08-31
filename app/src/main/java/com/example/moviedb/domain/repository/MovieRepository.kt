package com.example.moviedb.domain.repository

import com.example.moviedb.core.local.entity.MovieEntity
import com.example.moviedb.data.model.MovieResponse

interface MovieRepository {
    suspend fun getAllMovies(page: Int): MovieResponse
    suspend fun insertMovie(movie: MovieEntity)
    suspend fun deleteMovie(movieId: Int)
    suspend fun doesMovieExist(movieId: Int): Boolean
    suspend fun getFavoriteMovies(): List<MovieEntity>
}