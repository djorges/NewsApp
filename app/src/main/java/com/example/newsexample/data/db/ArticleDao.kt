package com.example.newsexample.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(articles: List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(article: ArticleEntity)

    @Query("SELECT * FROM articles WHERE url = :url LIMIT 1")
    suspend fun getByUrl(url: String): ArticleEntity?

    @Query("SELECT * FROM articles")
    fun getAll(): Flow<List<ArticleEntity>>

    @Query("SELECT COUNT(*) > 0 FROM articles WHERE url = :url")
    suspend fun isSaved(url: String): Boolean

    @Delete
    suspend fun delete(article: ArticleEntity)

    @Query("DELETE FROM articles WHERE url = :url")
    suspend fun deleteByUrl(url: String)
}