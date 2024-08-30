package com.example.moviedb.data.service

import com.example.moviedb.core.remote.utils.Endpoints.DISCOVER
import com.example.moviedb.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GetMoviesAPIService {
    @GET(DISCOVER)
    suspend fun discoverMovies(
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int,
    ): MovieResponse
}