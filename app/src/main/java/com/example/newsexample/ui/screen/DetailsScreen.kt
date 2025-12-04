package com.example.newsexample.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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


/**
 *
 * TODO:
 *
 * */
@Composable
fun DetailScreen(
    article: Article,
    onAddToFavorites: () -> Unit = {},
    onShare: () -> Unit = {},
    onDetails: () -> Unit= {},
    onRateUp: () -> Unit = {},
    onRateDown: () -> Unit = {},
) {
    val serif = FontFamily.Serif
    var menuExpanded by remember { mutableStateOf(false) }
    //TODO: Create viewModel, then implement actions

    Column(modifier = Modifier.fillMaxWidth()){
        // Imagen principal
        article.urlToImage?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
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
                    Icon(Icons.Default.Person, contentDescription = null)
                    Text(
                        text = article.author ?: stringResource(R.string.details_text_default_author),
                        fontFamily = serif,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(R.drawable.baseline_calendar_today_24), contentDescription = null)
                    Text(
                        text = article.publishedAt ?: stringResource(R.string.details_text_default_date),
                        fontFamily = serif,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
                // Rate Up / Down
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onRateUp) {
                        Icon(painterResource(R.drawable.baseline_thumb_up_off_alt_24), contentDescription = "Rate Up")
                    }

                    IconButton(onClick = onRateDown) {
                        Icon(painterResource(R.drawable.outline_thumb_down_24), contentDescription = "Rate Down")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                    onClick = { menuExpanded = true }
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.details_menu_description))
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.details_menu_action_favorites)) },
                        onClick = {
                            menuExpanded = false
                            onAddToFavorites()
                        },
                        leadingIcon = {
                            Icon(painterResource(R.drawable.outline_bookmarks_24), contentDescription = null)
                        }
                    )


                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.details_menu_action_share)) },
                        onClick = {
                            menuExpanded = false
                            onShare()
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Share, contentDescription = null)
                        }
                    )

                    //TODO: This can be deleted
                    DropdownMenuItem(
                        text = { Text("Details") },
                        onClick = {
                            menuExpanded = false
                            onDetails()
                        },
                        leadingIcon = {
                            Icon(painterResource(R.drawable.baseline_info_outline_24), contentDescription = null)
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_9", showSystemUi = false,
)
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


    DetailScreen(
        article = sample
    )
}