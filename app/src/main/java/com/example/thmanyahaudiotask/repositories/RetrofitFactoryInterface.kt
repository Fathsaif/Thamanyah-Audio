package com.example.thmanyahaudiotask.repositories

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitFactoryInterface {
    fun buildRetrofit(): Retrofit
}

class RetrofitFactory(private val okHttpClientFactoryInterface: OkHttpClientFactoryInterface) :
    RetrofitFactoryInterface {
    override fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api-v2-b2sit6oh3a-uc.a.run.app/")
            .client(
                okHttpClientFactoryInterface.buildOkHttpClient(
                    loggerInterceptor = provideLoggingInterceptor(),
                )
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}