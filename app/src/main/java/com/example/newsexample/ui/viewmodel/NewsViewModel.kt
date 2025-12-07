package com.example.newsexample.ui.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.NewsResponse
import com.example.newsexample.data.repository.NewsRepository
import com.example.newsexample.di.repositoryModule
import com.example.newsexample.ui.state.NewsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

open class NewsViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {
    private val _breakingNews = MutableStateFlow<NewsState>(NewsState.Empty)
    val breakingNews: StateFlow<NewsState> = _breakingNews.asStateFlow()

    private val _searchNews = MutableStateFlow<NewsState>(NewsState.Empty)
    val searchNews: StateFlow<NewsState> = _searchNews.asStateFlow()

    private val _selectedCountryCode = MutableStateFlow("us")
    val selectedCountryCode: StateFlow<String> = _selectedCountryCode.asStateFlow()

    private val _selectedCategory = MutableStateFlow("general")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _selectedPageSize = MutableStateFlow(22)
    val selectedPageSize: StateFlow<Int> = _selectedPageSize.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()


    init {
        getTopHeadLinesNews()
    }

    fun setCountryCode(countryCode: String) {
        _selectedCountryCode.value = countryCode
    }
    fun setCategory(category: String) {
        _selectedCategory.value = category
    }
    fun setPageSize(pageSize: Int) {
        _selectedPageSize.value = pageSize
    }

    fun getTopHeadLinesNews() {
        viewModelScope.launch {
            _breakingNews.value = NewsState.Loading

            try {
                val response: Response<NewsResponse> = newsRepository.getTopHeadLinesNews(_selectedCountryCode.value, _selectedCategory.value, _selectedPageSize.value)
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
            _searchNews.value = NewsState.Loading

            try {
                val response: Response<NewsResponse> = newsRepository.getSearchNews(searchKeyWord)
                if(response.isSuccessful && response.body() != null){
                    _searchNews.value = NewsState.Success(response.body()!!)
                }else{
                    _searchNews.value = NewsState.Error(response.message())
                }
            }catch (e: Exception){
                _searchNews.value = NewsState.Error(e.message.toString())
            }
        }
    }

    fun formatDate(dateString: String?):String? {
       return newsRepository.formatDateUtil(dateString)
    }


    suspend fun isSaved(url: String): Boolean {
        return newsRepository.isArticleSaved(url)
    }

    fun toggleSaved(article: Article) = viewModelScope.launch {
        article.url?.let{
            if (isSaved(article.url)) {
                newsRepository.deleteArticle(article)
            } else {
                newsRepository.saveArticle(article)
            }
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.saveArticle(article)
        }
    }



    val savedArticles = newsRepository.getAllArticles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )



    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent: SharedFlow<String> = _uiEvent

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)

        _uiEvent.emit("Article deleted.")
    }
}

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}