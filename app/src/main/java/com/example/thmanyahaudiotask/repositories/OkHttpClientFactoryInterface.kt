package com.example.thmanyahaudiotask.repositories

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface OkHttpClientFactoryInterface {
    fun buildOkHttpClient(
        loggerInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient
}

class OkHttpClientFactory : OkHttpClientFactoryInterface {
    override fun buildOkHttpClient(
        loggerInterceptor: HttpLoggingInterceptor,

        ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggerInterceptor)

        return builder.build()
    }
}
