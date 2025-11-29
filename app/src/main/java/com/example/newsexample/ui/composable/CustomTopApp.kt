package com.example.newsexample.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String?,
    modifier: Modifier = Modifier,
    onSearchIconClicked: () -> Unit = {},
    onMenuIconClicked: () -> Unit = {},
    onBackIconClicked: () -> Unit = {},
    onUserSearchQuery:(String) -> Unit = {}
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }


    CenterAlignedTopAppBar(
        title = {
            if(title == Screens.SearchScreen.route && isSearchActive){
                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        onUserSearchQuery(searchText)
                    },
                    placeholder = { Text(text = "Search") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    )
                )
            }else{
                Text(text = title.orEmpty())
            }
        },
        navigationIcon = {
            if(title == Screens.HomeScreen.route){
                IconButton(
                    onClick = onMenuIconClicked
                ){
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu"
                    )
                }
            }else{
                IconButton(
                    onClick = {
                        if(isSearchActive){
                            isSearchActive = false
                            searchText = ""
                        }else{
                            onBackIconClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions={
            if(title in listOf(Screens.HomeScreen.route, Screens.SearchScreen.route)){
                IconButton(onClick = {
                    if(title == Screens.SearchScreen.route){
                        isSearchActive = true
                    }else{
                        onSearchIconClicked()
                    }

                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(alpha=0.5f),
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}