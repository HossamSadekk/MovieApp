package com.example.moviedb.domain.pagination

import androidx.paging.PagingSource
import com.example.moviedb.data.model.Movie
import com.example.moviedb.data.model.MovieResponse
import com.example.moviedb.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MoviePagingSourceTest {
    private lateinit var movieRepository: MovieRepository
    private lateinit var moviePagingSource: MoviePagingSource

    @Before
    fun setup() {
        movieRepository = mockk()
        moviePagingSource = MoviePagingSource(movieRepository)
    }

    @Test
    fun `load returns paged data when repository returns movies`() = runTest {
        // Arrange
        val page = 1
        val movies = listOf(
            Movie(
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
            ),
            Movie(
                id = 2,
                title = "Movie 2",
                overview = "Overview 2",
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
        )
        val movieResponse = MovieResponse(
            page = page,
            movies = movies,
            totalPages = 2,
            totalResults = 2
        )
        coEvery { movieRepository.getAllMovies(page) } returns movieResponse
        coEvery { movieRepository.getFavoriteMovies() } returns emptyList() // No liked movies

        // Act
        val result = moviePagingSource.load(PagingSource.LoadParams.Refresh(page, 20, false))

        // Assert
        assertTrue(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(movies.size, pageResult.data.size)
        assertEquals(null, pageResult.prevKey)
        assertEquals(2, pageResult.nextKey)
    }

    @Test
    fun `load returns error when repository throws IOException`() = runTest {
        // Arrange
        val page = 1
        val exception = IOException("Network error")
        coEvery { movieRepository.getAllMovies(page) } throws exception

        // Act
        val result = moviePagingSource.load(PagingSource.LoadParams.Refresh(page, 20, false))

        // Assert
        assertTrue(result is PagingSource.LoadResult.Error)
        val errorResult = result as PagingSource.LoadResult.Error
        assertEquals("Network error. Please check your internet connection.", errorResult.throwable.message)
    }

    @Test
    fun `load returns error when repository throws HttpException`() = runTest {
        // Arrange
        val page = 1
        val exception = HttpException(Response.error<Any>(401, ResponseBody.create(null, "")))
        coEvery { movieRepository.getAllMovies(page) } throws exception

        // Act
        val result = moviePagingSource.load(PagingSource.LoadParams.Refresh(page, 20, false))

        // Assert
        assertTrue(result is PagingSource.LoadResult.Error)
        val errorResult = result as PagingSource.LoadResult.Error
        assertEquals("Unauthorized access. Please check your API key.", errorResult.throwable.message)
    }

    @Test
    fun `load returns error when repository throws Exception`() = runTest {
        // Arrange
        val page = 1
        val exception = Exception("Unknown error")
        coEvery { movieRepository.getAllMovies(page) } throws exception

        // Act
        val result = moviePagingSource.load(PagingSource.LoadParams.Refresh(page, 20, false))

        // Assert
        assertTrue(result is PagingSource.LoadResult.Error)
        val errorResult = result as PagingSource.LoadResult.Error
        assertEquals("An unexpected error occurred: Unknown error", errorResult.throwable.message)
    }


}