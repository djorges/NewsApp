package com.example.newsexample.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsexample.R
import com.example.newsexample.data.api.Article
import com.example.newsexample.ui.composable.ArticleCard
import com.example.newsexample.ui.state.NewsState
import com.example.newsexample.ui.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onItemClick: (Article) -> Unit = {}
) {
    val newsState by viewModel.breakingNews.collectAsState()
    val countryCode by viewModel.selectedCountryCode.collectAsState()
    var showFilters by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val result = newsState) {
            is NewsState.Loading -> {
                CircularProgressIndicator()
            }
            is NewsState.Empty -> {
                Text(
                    text = stringResource(R.string.empty_list)
                )
            }
            is NewsState.Error -> {
                val snackbarHostState = viewModel.snackbarHostState

                LaunchedEffect(result.message) {
                    snackbarHostState.showSnackbar(
                        message = result.message,
                        actionLabel = context.getString(R.string.home_txt_dismiss),
                        duration = SnackbarDuration.Long
                    )
                }

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
                        ArticleCard(
                            article = article,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { onItemClick(article) })
                        )
                    }
                }
                Row {
                    FloatingActionButton(
                        onClick = { showFilters = true },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(
                            painterResource(R.drawable.baseline_filter_list_24), 
                            contentDescription = stringResource(R.string.dialog_txt_filters)
                        )
                    }
                }
            }
        }
    }

    if (showFilters) {
        AlertDialog(
            onDismissRequest = { showFilters = false },
            title = { Text(stringResource(R.string.dialog_txt_filters)) },
            text = {
                FilterContent(
                    selectedCountryCode = countryCode,
                    onCountrySelected = { viewModel.setCountry(it) }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.applyFilters()
                        showFilters = false
                    }
                ) { Text(stringResource(R.string.dialog_btn_apply)) }
            },
            dismissButton = {
                TextButton(onClick = { showFilters = false }) {
                    Text(stringResource(R.string.dialog_btn_cancel))
                }
            }
        )
    }
}

@Composable
fun FilterContent(
    selectedCountryCode: String?,
    onCountrySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val countries = listOf(
        "ar" to "Argentina",
        "us" to "United States",
        "es" to "Spain",
        "mx" to "Mexico",
        "br" to "Brazil",
        "uk" to "United Kingdom"
    )

    Column {
        Text(stringResource(R.string.dialog_txt_country), fontSize = 14.sp)

        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(countries.find { it.first == selectedCountryCode }?.second ?: stringResource(R.string.dialog_btn_select_country))
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                countries.forEach { (code, name) ->
                    DropdownMenuItem(
                        text = { Text(name) },
                        onClick = {
                            onCountrySelected(code)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}