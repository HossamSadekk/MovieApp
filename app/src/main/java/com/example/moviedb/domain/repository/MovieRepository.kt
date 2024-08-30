package com.example.moviedb.domain.repository

import com.example.moviedb.data.model.MovieResponse

interface MovieRepository {
    suspend fun getAllMovies(page: Int): MovieResponse
}