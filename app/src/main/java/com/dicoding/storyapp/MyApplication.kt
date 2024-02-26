package com.dicoding.storyapp

import android.app.Application
import com.dicoding.storyapp.core.di.databaseModule
import com.dicoding.storyapp.core.di.datastoreModule
import com.dicoding.storyapp.core.di.networkModule
import com.dicoding.storyapp.core.di.preferencesModule
import com.dicoding.storyapp.core.di.repositoryModule
import com.dicoding.storyapp.di.useCaseModule
import com.dicoding.storyapp.di.viewModelModule
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
                    datastoreModule,
                    preferencesModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}