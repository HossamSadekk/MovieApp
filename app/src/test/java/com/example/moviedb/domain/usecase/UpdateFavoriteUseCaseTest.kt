package com.example.moviedb.domain.usecase

import com.example.moviedb.core.mapper.toEntity
import com.example.moviedb.data.model.Movie
import com.example.moviedb.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateFavoriteUseCaseTest {
    private lateinit var movieRepository: MovieRepository
    private lateinit var updateFavoriteUseCase: UpdateFavoriteUseCase

    @Before
    fun setup() {
        movieRepository = mockk()
        updateFavoriteUseCase = UpdateFavoriteUseCase(movieRepository)
    }

    @Test
    fun deleteMovieWhenExists() = runTest {
        val movie = Movie(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            backdropPath = "",
            posterPath = "",
            adult = false,
            genreIds = emptyList(),
            originalLanguage = "",
            originalTitle = "",
            popularity = 0.0,
            releaseDate = "",
            voteAverage = 0.0,
            voteCount = 0,
            video = false
        )
        coEvery { movieRepository.doesMovieExist(movie.id) } returns true
        coJustRun { movieRepository.deleteMovie(movie.id) }

        updateFavoriteUseCase(movie)

        coVerify { movieRepository.deleteMovie(movie.id) }
        coVerify(exactly = 0) { movieRepository.insertMovie(any()) }
    }

    @Test
    fun `invoke inserts movie when it does not exist`() = runTest {
        // Arrange
        val movie = Movie(
            id = 1,
            title = "Movie 1",
            overview = "Overview 1",
            backdropPath = "",
            posterPath = "",
            adult = false,
            genreIds = emptyList(),
            originalLanguage = "",
            originalTitle = "",
            popularity = 0.0,
            releaseDate = "",
            voteAverage = 0.0,
            voteCount = 0,
            video = false
        )
        coEvery { movieRepository.doesMovieExist(movie.id) } returns false
        coJustRun { movieRepository.insertMovie(movie.toEntity()) }

        // Act
        updateFavoriteUseCase(movie)

        // Assert
        coVerify { movieRepository.insertMovie(movie.toEntity()) }
        coVerify(exactly = 0) { movieRepository.deleteMovie(any()) }
    }

}