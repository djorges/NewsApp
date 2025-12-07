package com.example.newsexample.data.repository

import com.example.newsexample.data.api.ApiService
import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.NewsResponse
import com.example.newsexample.data.db.ArticleDao
import com.example.newsexample.data.db.ArticleEntity
import com.example.newsexample.domain.toDomain
import com.example.newsexample.domain.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.text.format

class NewsRepositoryImpl(
    private val apiService: ApiService,
    private val articleDao: ArticleDao
): NewsRepository {
    override suspend fun getTopHeadLinesNews(countryCode: String, category: String, pageSize: Int): Response<NewsResponse> {
        return apiService.getTopHeadLinesNews(countryCode, category, pageSize)
    }

    override suspend fun getSearchNews(searchKeyWord: String): Response<NewsResponse> {
        return apiService.getSearchNews(searchKeyWord)
    }


    override suspend fun saveArticle(article: Article):Unit = articleDao.save(article.toEntity())

    override suspend fun deleteArticle(article: Article):Unit = articleDao.delete(article.toEntity())

    override fun getAllArticles():Flow<List<Article>> = articleDao.getAll().map { listOfEntities ->
        listOfEntities.map { entity ->
            entity.toDomain()
        }
    }

    override suspend fun isArticleSaved(url: String): Boolean = articleDao.isSaved(url)

    override fun formatDateUtil(dateString: String?): String? {
        if (dateString.isNullOrBlank()) {
            return null
        }

        val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())

        return try {
            val date = OffsetDateTime.parse(dateString, inputFormatter)
            date.format(outputFormatter)
        } catch (e: Exception) {
            null
        }
    }
}