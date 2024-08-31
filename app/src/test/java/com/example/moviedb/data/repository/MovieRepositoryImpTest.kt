package com.example.moviedb.data.repository

import com.example.moviedb.core.local.dao.MovieDao
import com.example.moviedb.core.local.entity.MovieEntity
import com.example.moviedb.data.model.MovieResponse
import com.example.moviedb.data.service.GetMoviesAPIService
import com.example.moviedb.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieRepositoryImpTest {
    private lateinit var repository: MovieRepository

    private val apiService: GetMoviesAPIService = mockk()
    private val movieDao: MovieDao = mockk()

    @Before
    fun setUp() {
        repository = MovieRepositoryImp(apiService, movieDao)
    }

    @Test
    fun `getAllMovies from API - success`() = runTest {
        // Given
        val mockResponse = MovieResponse(1, emptyList(), 1, 1)
        coEvery { apiService.discoverMovies(any(), any(), any()) } returns mockResponse

        // When
        val result = repository.getAllMovies(page = 1)

        // Then
        assertEquals(mockResponse, result)
        coVerify { apiService.discoverMovies(any(), any(), any()) }
    }

    @Test
    fun `getAllMovies from database - success`() = runTest {
        // Given
        val mockMovies = listOf(MovieEntity(1, "Title", 2.0, "12-2-2024", "BackdropPath"))
        coEvery { movieDao.getAllMovies() } returns mockMovies

        // When
        val result = repository.getFavoriteMovies()

        // Then
        assertEquals(mockMovies, result)
        coVerify { movieDao.getAllMovies() }
    }

    @Test
    fun `insertMovie - success`() = runTest {
        // Given
        val mockMovies = MovieEntity(1, "Title", 2.0, "12-2-2024", "BackdropPath")
        coEvery { movieDao.insertMovie(mockMovies) } returns Unit

        // When
        repository.insertMovie(mockMovies)

        // Then
        coVerify { movieDao.insertMovie(mockMovies) }
    }

    @Test
    fun `deleteMovie - success`() = runTest {
        // Given
        val movieId = 1
        coEvery { movieDao.deleteLikedMovie(movieId) } returns Unit

        // When
        repository.deleteMovie(movieId)

        // Then
        coVerify { movieDao.deleteLikedMovie(movieId) }
    }

    @Test
    fun `doesMovieExist - movie exists`() = runTest {
        // Given
        val movieId = 1
        coEvery { movieDao.doesMovieExist(movieId) } returns true

        // When
        val result = repository.doesMovieExist(movieId)

        // Then
        assertTrue(result)
        coVerify { movieDao.doesMovieExist(movieId) }
    }

    @Test
    fun `doesMovieExist - movie does not exist`() = runTest {
        // Given
        val movieId = 1
        coEvery { movieDao.doesMovieExist(movieId) } returns false

        // When
        val result = repository.doesMovieExist(movieId)

        // Then
        assertFalse(result)
        coVerify { movieDao.doesMovieExist(movieId) }
    }

}