package com.example.newsexample

import android.app.Application
import com.example.newsexample.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp: Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(databaseModule)
        }
    }
}