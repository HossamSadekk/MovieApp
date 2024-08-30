package com.example.moviedb.ui.movieDetails.viewmodel

import com.example.moviedb.core.base.viewmodel.BaseViewModel
import com.example.moviedb.data.model.Movie
import com.example.moviedb.domain.usecase.UpdateFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val updateFavoriteUseCase: UpdateFavoriteUseCase) :
    BaseViewModel() {
    fun updateMovieState(movie: Movie) = safeLaunch {
        updateFavoriteUseCase.invoke(movie)
    }
}