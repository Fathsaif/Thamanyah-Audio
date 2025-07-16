package com.example.thmanyahaudiotask

import android.app.Application
import com.example.thmanyahaudiotask.di.homeModule
import com.example.thmanyahaudiotask.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ThamanyaApp : Application() {

    companion object {
        lateinit var instance: ThamanyaApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@ThamanyaApp)
            modules(
                listOf(
                    networkModule,
                    homeModule
                )
            )
        }
    }
}