package com.jagoteori.foodrecipesapp

import android.app.Application
import com.jagoteori.foodrecipesapp.di.firebaseModule
import com.jagoteori.foodrecipesapp.di.repositoryModule
import com.jagoteori.foodrecipesapp.di.useCaseModule
import com.jagoteori.foodrecipesapp.di.viewModelModule
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
            modules(listOf(firebaseModule, repositoryModule, viewModelModule, useCaseModule))
        }
    }
}