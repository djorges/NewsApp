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

@Singleton(binds = [NewsRepository::class])
class NewsRepositoryImpl(
    private val apiService: ApiService,
    private val articleDao: ArticleDao
) : NewsRepository {

    override suspend fun getTopHeadLinesNews(
        countryCode: String,
        category: String,
        pageSize: Int
    ): Response<NewsResponse> {
        return apiService.getTopHeadLinesNews(
            countryCode,
            category,
            pageSize
        )
    }

    override suspend fun getSearchNews(
        searchKeyWord: String
    ): Response<NewsResponse> {
        return apiService.getSearchNews(searchKeyWord.trim())
    }

    override suspend fun getNewsByCategory(
        category: String,
        countryCode: String,
        pageSize: Int
    ): Response<NewsResponse> {
        return apiService.getTopHeadLinesNews(
            countryCode,
            category,
            pageSize
        )
    }

    override suspend fun getLatestNews(
        countryCode: String,
        pageSize: Int
    ): Response<NewsResponse> {
        return apiService.getTopHeadLinesNews(
            countryCode,
            "general",
            pageSize
        )
    }

    override suspend fun saveArticle(article: Article) {
        articleDao.save(article.toEntity())
    }

    override suspend fun deleteArticle(article: Article) {
        articleDao.delete(article.toEntity())
    }

    override fun getAllArticles(): Flow<List<Article>> {
        return articleDao.getAll()
            .map { entities -> entities.map { it.toDomain() } }
            .distinctUntilChanged()
    }

    override suspend fun isArticleSaved(url: String): Boolean {
        return articleDao.isSaved(url)
    }

    override suspend fun getSavedArticleByUrl(url: String): Article? {
        return articleDao.getByUrl(url)?.toDomain()
    }

    override suspend fun getSavedArticlesCount(): Int {
        return articleDao.getArticlesCount()
    }

    override suspend fun deleteAllArticles() {
        articleDao.deleteAll()
    }

    override fun searchSavedArticles(query: String): Flow<List<Article>> {
        return articleDao.searchArticles(query.trim())
            .map { entities -> entities.map { it.toDomain() } }
            .distinctUntilChanged()
    }

    override suspend fun toggleSavedArticle(article: Article): Boolean {
        return if (articleDao.isSaved(article.url)) {
            articleDao.deleteByUrl(article.url)
            false
        } else {
            articleDao.save(article.toEntity())
            true
        }
    }

    override fun formatDateUtil(dateString: String?): String? {
        if (dateString.isNullOrBlank()) return null

        return runCatching {
            OffsetDateTime.parse(dateString)
                .format(
                    DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy",
                        Locale.getDefault()
                    )
                )
        }.getOrNull()
    }
}
