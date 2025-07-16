package com.example.thmanyahaudiotask.di

import com.example.thmanyahaudiotask.repositories.OkHttpClientFactory
import com.example.thmanyahaudiotask.repositories.OkHttpClientFactoryInterface
import com.example.thmanyahaudiotask.repositories.RetrofitFactory
import com.example.thmanyahaudiotask.repositories.RetrofitFactoryInterface
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val networkModule = module {
    single { provideLoggingInterceptor() }
    single<OkHttpClientFactoryInterface> { OkHttpClientFactory() }
    single<RetrofitFactoryInterface> { RetrofitFactory(get()) }
}

fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}