package com.example.moviedb.domain.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviedb.data.model.Movie
import com.example.moviedb.domain.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(private val movieRepository: MovieRepository) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = movieRepository.getAllMovies(page = page)
            LoadResult.Page(
                data = response.movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Return the key to refresh the list, or null if none is available
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}