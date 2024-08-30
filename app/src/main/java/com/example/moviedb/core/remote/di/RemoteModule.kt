package com.example.moviedb.core.remote.di

import com.example.moviedb.core.remote.helpers.createOkHttpClient
import com.example.moviedb.core.remote.utils.Rout.API_KEY
import com.example.moviedb.core.remote.utils.Rout.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    @Named("API_KEY")
    fun provideAPIKEY(): String {
        return API_KEY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named(value = "API_KEY") apiKey: String,
    ): OkHttpClient {
        return createOkHttpClient(
            apiKey = apiKey
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}