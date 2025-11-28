package com.example.newsexample.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsexample.R
import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.Source
import com.example.newsexample.ui.composable.SimpleArticleUI

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ){
        items(3){
            SimpleArticleUI(
                article = Article(
                    title = stringResource(R.string.title),
                    description = stringResource(R.string.description),
                    source = Source(name = stringResource(R.string.newspaper)),
                    publishedAt = stringResource(R.string.publish_date)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}