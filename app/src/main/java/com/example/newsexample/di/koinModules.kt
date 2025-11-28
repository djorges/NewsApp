package com.example.newsexample.di

import androidx.room.Room
import com.example.newsexample.data.api.ApiService
import com.example.newsexample.data.db.AppDatabase
import com.example.newsexample.data.repository.NewsRepository
import com.example.newsexample.data.repository.NewsRepositoryImpl
import com.example.newsexample.ui.viewmodel.NewsViewModel
import com.example.newsexample.data.api.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
    single { get<AppDatabase>().articleDao() }
}

val apiModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(ApiService::class.java) }
}

val repositoryModule = module{
    singleOf(::NewsRepositoryImpl){ bind<NewsRepository>()}
    viewModelOf(::NewsViewModel)
}