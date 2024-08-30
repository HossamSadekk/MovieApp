package com.example.moviedb.domain.di

import com.example.moviedb.data.repository.MovieRepositoryImp
import com.example.moviedb.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DomainModule {
    @Binds
    abstract fun bindMovieRepository(movieRepositoryImp: MovieRepositoryImp): MovieRepository

}