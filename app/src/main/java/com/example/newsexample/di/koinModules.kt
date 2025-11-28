package com.example.newsexample.di

import androidx.room.Room
import com.example.newsexample.data.api.ApiService
import com.example.newsexample.data.db.AppDatabase
import com.example.newsexample.data.db.ArticleDao
import com.example.newsexample.utils.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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
            .fallbackToDestructiveMigration()
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

val viewmodelModule = module{
    /*viewModel {
        MainViewModel(get())
    }*/
}