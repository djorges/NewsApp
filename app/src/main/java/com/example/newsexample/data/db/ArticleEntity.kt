package com.example.newsexample.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsexample.data.api.Source

@Entity(
    tableName = "articles"
)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "author", defaultValue = "")
    val author: String? = null,
    val title: String = "",
    val content: String = "",
    val description: String = "",
    @ColumnInfo(name = "published_at")
    val publishedAt: String = "",
    val source: Source,
    val url: String = "",
    @ColumnInfo(name = "url_to_image")
    val urlToImage: String? = null
)
