package com.example.newsexample.data.api

import com.example.newsexample.data.api.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * Api call example:
 * https://newsapi.org/v2/everything?q=tesla&from=2025-10-23&sortBy=publishedAt&apiKey=
 * */


interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadLinesNews(
        @Query("country") countryCode:String,
        @Query("category") category:String,
        @Query("pageSize") pageSize:Int,
        @Query("apiKey") apiKey:String = Constants.API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getSearchNews(
        @Query("q") searchKeyWord:String,
        @Query("apiKey") apiKey:String = Constants.API_KEY
    ): Response<NewsResponse>
}