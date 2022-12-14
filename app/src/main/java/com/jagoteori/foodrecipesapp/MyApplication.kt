package com.jagoteori.foodrecipesapp

import android.app.Application
import com.jagoteori.foodrecipesapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    firebaseModule,
                    remoteDataSource,
                    repositoryModule,
                    viewModelModule,
                    useCaseModule
                )
            )
        }
    }
}