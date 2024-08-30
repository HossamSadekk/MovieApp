package com.example.moviedb.data.repository

import com.example.moviedb.core.local.dao.MovieDao
import com.example.moviedb.core.local.entity.MovieEntity
import com.example.moviedb.data.model.MovieResponse
import com.example.moviedb.data.service.GetMoviesAPIService
import com.example.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val getMoviesAPIService: GetMoviesAPIService,
    private val movieDao: MovieDao,
) : MovieRepository {
    override suspend fun getAllMovies(page: Int): MovieResponse = getMoviesAPIService.discoverMovies(page = page)
    override suspend fun getAllMovies(): List<MovieEntity> = movieDao.getAllMovies()
    override suspend fun insertMovie(movie: MovieEntity) = movieDao.insertMovie(movie)
    override suspend fun deleteMovie(movieId: Int) = movieDao.deleteLikedMovie(movieId)
    override suspend fun doesMovieExist(movieId: Int): Boolean = movieDao.doesMovieExist(movieId)
}