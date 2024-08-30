package com.example.moviedb.ui.movieList.viewmodel

import androidx.paging.PagingData
import com.example.moviedb.core.base.viewmodel.BaseViewModel
import com.example.moviedb.data.model.Movie
import com.example.moviedb.domain.usecase.GetMoviesUseCase
import com.example.moviedb.domain.usecase.UpdateFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
) : BaseViewModel() {
    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> = _movies

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        collectFlow(
            flow = getMoviesUseCase.execute(),
            onCollect = { pagingData ->
                _movies.value = pagingData
            }
        )
    }

    fun updateMovieState(movie: Movie) = safeLaunch {
        updateFavoriteUseCase.invoke(movie)
    }

}