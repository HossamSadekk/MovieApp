package com.example.moviedb.core.remote.helpers

import com.example.moviedb.core.remote.interceptors.ApiKeyInterceptor
import com.example.moviedb.core.remote.interceptors.TimberLoggingInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

private const val CLIENT_TIME_OUT = 60L
fun createApiKeyInterceptor(apiKey: String): ApiKeyInterceptor = ApiKeyInterceptor(apiKey)

fun createTimberLoggingInterceptor(): TimberLoggingInterceptor = TimberLoggingInterceptor()

fun createOkHttpClient(
    apiKey: String,
): OkHttpClient {
    return OkHttpClient.Builder().apply {
        addInterceptor(createTimberLoggingInterceptor())
        addInterceptor(createApiKeyInterceptor(apiKey))
        //specifies the maximum amount of time that OkHttp will wait for the server to respond to the connection request
        connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        //controls how long OkHttp will wait for the response data to be sent by the server.
        readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        //OkHttp will wait to send data to the server before giving up and throwing a SocketTimeoutException.
        writeTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
    }.build()
}