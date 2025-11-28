package com.example.newsexample.ui.state

import com.example.newsexample.data.api.NewsResponse


sealed class NewsState {
    object Loading : NewsState()
    data class Success(val data: NewsResponse): NewsState()
    data class Error(val message: String): NewsState()
    object Empty : NewsState()
}