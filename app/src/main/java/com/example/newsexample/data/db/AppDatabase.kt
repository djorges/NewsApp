package com.example.newsexample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    SourceConverter::class
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun articleDao(): ArticleDao
}