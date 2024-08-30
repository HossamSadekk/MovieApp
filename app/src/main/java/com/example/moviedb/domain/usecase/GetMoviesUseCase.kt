package com.example.moviedb.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviedb.core.base.use_case.FlowPagingUseCase
import com.example.moviedb.data.model.Movie
import com.example.moviedb.domain.pagination.MoviePagingSource
import com.example.moviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) : FlowPagingUseCase<Movie>() {
    override fun execute(parameter: String?): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(repository) }
        ).flow
}