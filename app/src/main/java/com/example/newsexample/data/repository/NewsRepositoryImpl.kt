package com.example.newsexample.data.repository

import com.example.newsexample.data.api.ApiService
import com.example.newsexample.data.api.NewsResponse
import retrofit2.Response

class NewsRepositoryImpl(
    private val apiService: ApiService,
): NewsRepository {
    override suspend fun getTopHeadLinesNews(countryCode: String): Response<NewsResponse> {
        return apiService.getTopHeadLinesNews(countryCode)
    }

    override suspend fun getSearchNews(searchKeyWord: String): Response<NewsResponse> {
        return apiService.getSearchNews(searchKeyWord)
    }
}