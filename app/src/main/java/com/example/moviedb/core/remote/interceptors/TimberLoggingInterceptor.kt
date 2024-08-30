package com.example.moviedb.core.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class TimberLoggingInterceptor : Interceptor {

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Timber.tag("OkHttp").d(message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Timber.i("Sending request to ${request.url} with headers ${request.headers}")

        val response = loggingInterceptor.intercept(chain)
        Timber.i("Received response from ${response.request.url} with status code ${response.code}")

        return response
    }
}