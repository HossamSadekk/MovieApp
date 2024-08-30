package com.example.moviedb.domain.usecase

import com.example.moviedb.core.mapper.toEntity
import com.example.moviedb.data.model.Movie
import com.example.moviedb.domain.repository.MovieRepository
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(movie: Movie) {
        val doesMovieExist = movieRepository.doesMovieExist(movie.id)
        if (doesMovieExist) {
            movieRepository.deleteMovie(movie.id)
        } else {
            movieRepository.insertMovie(movie.toEntity())
        }
    }
}