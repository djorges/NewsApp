package com.example.newsexample.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.newsexample.R
import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.Source
import androidx.core.net.toUri
import com.example.newsexample.AppPreview
import com.example.newsexample.ui.theme.NewsExampleTheme
import com.example.newsexample.ui.viewmodel.NewsViewModel


/**
 *
 * TODO:
 *
 * */
@Composable
fun DetailScreen(
    article: Article,
    viewModel: NewsViewModel,
    snackbarHostState: SnackbarHostState
) {
    val uiEvent = viewModel.uiEvent.collectAsState(initial = null)

    LaunchedEffect(uiEvent.value) {
        uiEvent.value?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    ContentDetailScreen(
        article = article,
        onSaveClick = { viewModel.saveArticle(article) },
        formatDate = { viewModel.formatDate(article.publishedAt) }
    )
}

@Composable
fun ContentDetailScreen(
    article: Article,
    onRateUp: () -> Unit = {},
    onRateDown: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    formatDate: (String?) -> String? = { null }
) {
    val serif = FontFamily.Serif
    val context = LocalContext.current

    //Actions size and padding
    val smallPadding = PaddingValues(8.dp)
    val smallIconSize = 20.dp
    val smallTextSize = 12.sp

    Column(modifier = Modifier.fillMaxWidth()){
        // Article Image
        article.urlToImage?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.newslogo),
                error = painterResource(R.drawable.outline_error_24)
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Column(modifier = Modifier.padding(16.dp)) {

            // Title
            Text(
                text = article.title,
                fontFamily = serif,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Metadata
            Row(verticalAlignment = Alignment.CenterVertically){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = stringResource(R.string.details_text_default_author),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    Text(
                        text = article.author ?: stringResource(R.string.details_text_default_author),
                        fontFamily = serif,
                        modifier = Modifier.padding(start = 6.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.baseline_calendar_today_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    Text(
                        text = formatDate(article.publishedAt) ?: stringResource(R.string.details_text_default_date),
                        fontFamily = serif,
                        modifier = Modifier.padding(start = 6.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            //Actions
            Row(verticalAlignment = Alignment.CenterVertically) {

                TextButton(
                    onClick = onSaveClick,
                    contentPadding = smallPadding
                ) {
                    Icon(
                        painterResource(R.drawable.outline_bookmarks_24),
                        contentDescription = stringResource(R.string.details_menu_action_favorites),
                        modifier = Modifier.size(smallIconSize)
                    )
                    Text(
                        text = stringResource(R.string.details_menu_action_favorites),
                        fontFamily = serif,
                        fontSize = smallTextSize,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                TextButton(
                    onClick = {

                        //Share action
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, article.url ?: "")
                            putExtra(Intent.EXTRA_SUBJECT, article.title)
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    } ,
                    contentPadding = smallPadding
                ) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = stringResource(R.string.details_menu_action_share),
                        modifier = Modifier.size(smallIconSize)
                    )
                    Text(
                        stringResource(R.string.details_menu_action_share),
                        fontFamily = serif,
                        fontSize = smallTextSize,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {

                    TextButton(
                        onClick = onRateUp,
                        contentPadding = smallPadding
                    ) {
                        Icon(
                            painterResource(R.drawable.baseline_thumb_up_off_alt_24),
                            contentDescription = stringResource(R.string.details_btn_rateup),
                            modifier = Modifier.size(smallIconSize)
                        )
                    }

                    TextButton(
                        onClick = onRateDown,
                        contentPadding = smallPadding
                    ) {
                        Icon(
                            painterResource(R.drawable.outline_thumb_down_24),
                            contentDescription = stringResource(R.string.details_btn_ratedown),
                            modifier = Modifier.size(smallIconSize)
                        )
                    }
                }
            }

            // Description
            article.description?.let {
                Text(
                    text = it,
                    fontFamily = serif,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Actions
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {

                FloatingActionButton(
                    onClick = {
                        //Navigate to URL
                        article.url?.let { url ->
                            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                            context.startActivity(intent)
                        }
                    },

                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.details_menu_description)
                    )
                }
            }
        }
    }
}



@AppPreview
@Composable
fun DetailScreenPreview() {

    val sample = Article(
        author = "Juan Pérez",
        content = "Contenido extenso de ejemplo para el artículo.",
        description = "Una breve descripción del artículo de prueba",
        publishedAt = "2025-01-10",
        source = Source(id = "1", name = "Diario Ejemplo"),
        title = "Título de Prueba Estilo Diario",
        url = "https://ejemplo.com",
        urlToImage = "https://picsum.photos/800/400"
    )

    NewsExampleTheme {
        ContentDetailScreen(
            article = sample
        )
    }
}