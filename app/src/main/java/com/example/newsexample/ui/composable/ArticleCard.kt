package com.example.newsexample.ui.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.newsexample.AppPreview
import com.example.newsexample.R
import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.Source
import com.example.newsexample.ui.theme.NewsExampleTheme


/**
 *
 * TODO:
 * */

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
){
        Row(
            modifier = modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            AsyncImage(
                model = article.urlToImage,
                contentDescription = stringResource(R.string.article_img_default),
                modifier = Modifier
                    .size(width = 120.dp, height = 120.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.newslogo),
                error = painterResource(R.drawable.outline_error_24)
            )

            Column(modifier = Modifier.weight(1f)) {
                // Title
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = article.description ?: stringResource(R.string.article_text_description_default),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Source
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = stringResource(R.string.article_text_source_default),
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = article.source?.name ?: stringResource(R.string.article_text_source_default),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                // Publication Date
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(R.string.article_text_date_default),
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = article.publishedAt?.take(10) ?: stringResource(R.string.article_text_date_default),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 36)
@Composable
private fun Preview(){
    NewsExampleTheme {
        Surface {
            ArticleCard(
                article = Article(
                    author = "Juan Pérez",
                    content = "Contenido extenso de ejemplo para el artículo.",
                    description = "Una breve descripción del artículo de prueba",
                    publishedAt = "2025-01-10",
                    source = Source(id = "1", name = "Diario Ejemplo"),
                    title = "Título de Prueba Estilo Diario",
                    url = "https://ejemplo.com",
                    urlToImage = "https://picsum.photos/800/400"
                )
            )
        }
    }
}
