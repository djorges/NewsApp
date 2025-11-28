package com.example.newsexample.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsexample.data.api.NewsResponse
import com.example.newsexample.data.repository.NewsRepository
import com.example.newsexample.ui.state.NewsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {
    private val _breakingNews = MutableStateFlow<NewsState>(NewsState.Empty)
    val breakingNews: StateFlow<NewsState> = _breakingNews.asStateFlow()


    init {
        getTopHeadLinesNews("us")
    }

    fun getTopHeadLinesNews(countryCode: String) {
        viewModelScope.launch {
            _breakingNews.value = NewsState.Loading

            try {
                val response: Response<NewsResponse> = newsRepository.getTopHeadLinesNews(countryCode)
                if(response.isSuccessful && response.body() != null){
                    _breakingNews.value = NewsState.Success(response.body()!!)
                }else{
                    _breakingNews.value = NewsState.Error(response.message())
                }
            }catch (e: Exception){
                _breakingNews.value = NewsState.Error(e.message.toString())
            }
        }
    }

    fun getSearchNews(searchKeyWord: String) {
        viewModelScope.launch {
            _breakingNews.value = NewsState.Loading

            try {
                val response: Response<NewsResponse> = newsRepository.getSearchNews(searchKeyWord)
                if(response.isSuccessful && response.body() != null){
                    _breakingNews.value = NewsState.Success(response.body()!!)
                }else{
                    _breakingNews.value = NewsState.Error(response.message())
                }
            }catch (e: Exception){
                _breakingNews.value = NewsState.Error(e.message.toString())
            }
        }
    }
}