package com.example.moviedb.core.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.moviedb.core.local.db.MovieDatabase
import com.example.moviedb.core.local.entity.MovieEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class MovieDaoTest {
    private lateinit var movieDao: MovieDao
    private lateinit var database: MovieDatabase

    @get:Rule
    var instateExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).build()
        movieDao = database.movieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertMovie_shouldSuccessfullyInsertMovie() = runBlocking {
        val movie = MovieEntity(1, "Title", 2.0, "12-2-2024", "BackdropPath")
        movieDao.insertMovie(movie)

        val movies = movieDao.getAllMovies()

        assertEquals(1, movies.size)
        assertEquals(movie, movies[0])
    }

    @Test
    fun deleteMovie_shouldRemoveMovie() = runBlocking {
        val movie = MovieEntity(
            id = 1,
            title = "Test Movie",
            voteAverage = 7.5,
            releaseDate = "2024-01-01",
            posterPath = "path/to/poster"
        )
        movieDao.insertMovie(movie)
        movieDao.deleteLikedMovie(movie.id)

        val movies = movieDao.getAllMovies()

        assertEquals(0, movies.size)
    }

    @Test
    fun doesMovieExist_shouldReturnTrueIfMovieExists() = runBlocking {
        val movie = MovieEntity(
            id = 1,
            title = "Test Movie",
            voteAverage = 7.5,
            releaseDate = "2024-01-01",
            posterPath = "path/to/poster"
        )
        movieDao.insertMovie(movie)

        val exists = movieDao.doesMovieExist(movie.id)

        assertEquals(true, exists)
    }

    @Test
    fun doesMovieExist_shouldReturnFalseIfMovieDoesNotExist() = runBlocking {
        val exists = movieDao.doesMovieExist(999) // ID that does not exist

        assertEquals(false, exists)
    }
}