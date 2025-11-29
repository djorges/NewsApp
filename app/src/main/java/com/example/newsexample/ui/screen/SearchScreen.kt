package com.example.newsexample.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsexample.data.api.Article
import com.example.newsexample.ui.composable.SimpleArticleUI
import com.example.newsexample.ui.state.NewsState
import com.example.newsexample.ui.viewmodel.NewsViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel
){
    val searchState by viewModel.searchNews.collectAsState()
    var currentProgress by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val result = searchState) {
            is NewsState.Loading -> {
                LinearProgressIndicator(
                    modifier= Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.tertiary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
            is NewsState.Empty -> {
                Text(
                    text = "Empty"
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
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    state = rememberLazyListState()
                ){
                    items(result.data.articles, key = { article: Article ->
                        article.title
                    }){ article ->
                        SimpleArticleUI(
                            article = article,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}