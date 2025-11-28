package com.example.newsexample.data.repository

import com.example.newsexample.data.api.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getTopHeadLinesNews(countryCode: String): Response<NewsResponse>
    suspend fun getSearchNews(searchKeyWord: String): Response<NewsResponse>
}