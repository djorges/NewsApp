package com.example.newsexample.domain

import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.Source
import com.example.newsexample.data.db.ArticleEntity

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        author = this.author,
        title = this.title,
        content = this.content ?: "",
        description = this.description ?: "",
        publishedAt = this.publishedAt ?: "",
        source = this.source ?: Source(id = null, name = "Unknown"),
        url = this.url ?: "",
        urlToImage = this.urlToImage
    )
}

fun ArticleEntity.toDomain(): Article {
    return Article(
        author = this.author,
        title = this.title,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        source = this.source,
        url = this.url,
        urlToImage = this.urlToImage
    )
}