package com.example.moviedb.data.repository

import com.example.moviedb.data.model.MovieResponse
import com.example.moviedb.data.service.GetMoviesAPIService
import com.example.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val getMoviesAPIService: GetMoviesAPIService) : MovieRepository {
    override suspend fun getAllMovies(page: Int): MovieResponse = getMoviesAPIService.discoverMovies(page = page)
}