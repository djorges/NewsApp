package com.example.newsexample.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsexample.R
import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.Source
import com.example.newsexample.ui.composable.SimpleArticleUI
import com.example.newsexample.ui.state.NewsState
import com.example.newsexample.ui.viewmodel.NewsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel = koinViewModel()
) {
    val state = viewModel.breakingNews.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val result = state.value ) {
            is NewsState.Loading -> {
                CircularProgressIndicator()
            }
            is NewsState.Empty -> {
                Text(
                    text = stringResource(R.string.empty_list)
                )
            }
            is NewsState.Error -> {
                Text(
                    text = result.message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            is NewsState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(result.data.articles, key = { article: Article ->
                        article.title
                    }){ article ->
                        SimpleArticleUI(
                            article = article,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}