package com.example.moviedb.core.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviedb.core.local.dao.MovieDao
import com.example.moviedb.core.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
}