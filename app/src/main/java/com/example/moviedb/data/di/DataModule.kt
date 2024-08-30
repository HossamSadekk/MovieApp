package com.example.moviedb.data.di

import com.example.moviedb.data.service.GetMoviesAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Provides
    fun providesGetMoviesAPIService(
        retrofit: Retrofit,
    ): GetMoviesAPIService {
        return retrofit.create(GetMoviesAPIService::class.java)
    }
}