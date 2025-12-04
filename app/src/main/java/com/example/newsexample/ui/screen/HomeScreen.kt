package com.example.newsexample.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onItemClick: (Article) -> Unit = {}
) {
    val state by viewModel.breakingNews.collectAsState()
    val snackbarHostState = viewModel.snackbarHostState
    val scope = rememberCoroutineScope()

    /*
    scope.launch {
                    snackbarHostState.showSnackbar(
                        message = result.message,
                        actionLabel = "Dismiss",
                        duration = SnackbarDuration.Long
                    )
                }
    */

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val result = state) {
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
                                .clickable(onClick = { onItemClick(article) })
                        )
                    }
                }
            }
        }
    }
}