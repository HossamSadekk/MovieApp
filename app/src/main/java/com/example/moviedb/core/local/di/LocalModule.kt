package com.example.moviedb.core.local.di

import android.content.Context
import androidx.room.Room
import com.example.moviedb.core.local.dao.MovieDao
import com.example.moviedb.core.local.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesMovieDao(db: MovieDatabase): MovieDao =
        db.movieDao()
}