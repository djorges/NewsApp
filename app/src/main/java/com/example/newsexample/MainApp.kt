package com.example.newsexample

import android.app.Application
import com.example.newsexample.di.apiModule
import com.example.newsexample.di.databaseModule
import com.example.newsexample.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApp: Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(databaseModule, apiModule, repositoryModule)
        }
    }
}