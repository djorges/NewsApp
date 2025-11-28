package com.example.newsexample.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.newsexample.AppPreview
import com.example.newsexample.R
import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.Source

@Composable
fun SimpleArticleUI(
    modifier: Modifier = Modifier,
    article: Article
){
    Column(
        modifier = modifier.fillMaxWidth()
    ){
        Row{
            Column {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = "News Logo",
                    modifier = Modifier.size(width=160.dp, height=90.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.newslogo)
                )

                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = article.source.name,
                )
                Text(
                    text = article.publishedAt,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        lineHeight = 10.sp
                    ),
                    maxLines= 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = article.description,
                    style = TextStyle(
                        lineHeight = 10.sp
                    ),
                    maxLines= 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@AppPreview
@Composable
private fun Preview(){
    SimpleArticleUI(
        article = Article(
            title = stringResource(R.string.title),
            description = stringResource(R.string.description),
            source = Source(name = stringResource(R.string.newspaper)),
            publishedAt = stringResource(R.string.publish_date)
        )
    )
}