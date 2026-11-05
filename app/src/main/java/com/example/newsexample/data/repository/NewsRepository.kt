package com.example.newsexample.data.repository

import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.NewsResponse
import com.example.newsexample.domain.toDomain
import com.example.newsexample.domain.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response

interface NewsRepository {

    suspend fun getTopHeadLinesNews(
        countryCode: String,
        category: String,
        pageSize: Int
    ): Response<NewsResponse>

    suspend fun getSearchNews(
        searchKeyWord: String
    ): Response<NewsResponse>

    suspend fun getNewsByCategory(
        category: String,
        countryCode: String,
        pageSize: Int
    ): Response<NewsResponse>

    suspend fun getLatestNews(
        countryCode: String,
        pageSize: Int
    ): Response<NewsResponse>

    suspend fun saveArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getAllArticles(): Flow<List<Article>>

    suspend fun isArticleSaved(url: String): Boolean

    suspend fun getSavedArticleByUrl(url: String): Article?

    suspend fun getSavedArticlesCount(): Int

    suspend fun deleteAllArticles()

    fun searchSavedArticles(query: String): Flow<List<Article>>

    suspend fun toggleSavedArticle(article: Article): Boolean

    fun formatDateUtil(dateString: String?): String?
}
