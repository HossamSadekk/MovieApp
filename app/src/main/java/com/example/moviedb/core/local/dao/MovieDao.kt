package com.example.moviedb.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviedb.core.local.entity.MovieEntity
import com.example.moviedb.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovie(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteLikedMovie(movieId: Int)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM movies WHERE id = :movieId)")
    suspend fun doesMovieExist(movieId: Int): Boolean

}