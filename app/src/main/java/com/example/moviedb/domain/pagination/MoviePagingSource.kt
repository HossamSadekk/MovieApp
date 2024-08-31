package com.example.moviedb.domain.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedb.data.model.Movie
import com.example.moviedb.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val movieRepository: MovieRepository,
) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = movieRepository.getAllMovies(page = page)
            val likedMovies = movieRepository.getFavoriteMovies().map { it.id }.toSet()
            val updatedMovies = response.movies.map { movie ->
                movie.copy(isLiked = likedMovies.contains(movie.id))
            }
            LoadResult.Page(
                data = updatedMovies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(handleNetworkError(e))
        } catch (e: HttpException) {
            LoadResult.Error(handleHttpError(e))
        } catch (e: Exception) {
            LoadResult.Error(handleUnknownError(e))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Return the key to refresh the list, or null if none is available
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun handleNetworkError(exception: IOException): Exception {
        return Exception("Network error. Please check your internet connection.")
    }

    private fun handleHttpError(exception: HttpException): Exception {
        return when (exception.code()) {
            401 -> Exception("Unauthorized access. Please check your API key.")
            403 -> Exception("Forbidden. You might not have access to this resource.")
            404 -> Exception("Resource not found. Check the endpoint or URL.")
            500 -> Exception("Server error. Please try again later.")
            else -> Exception("HTTP error occurred: ${exception.code()}")
        }
    }

    private fun handleUnknownError(exception: Exception): Exception {
        return Exception("An unexpected error occurred: ${exception.message}")
    }
}