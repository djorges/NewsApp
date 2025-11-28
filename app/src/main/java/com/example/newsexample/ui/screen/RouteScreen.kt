package com.example.newsexample.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsexample.AppPreview
import com.example.newsexample.ui.composable.BottomAppBarUI
import com.example.newsexample.ui.composable.CustomTopAppBar
import com.example.newsexample.ui.composable.NavigationHostUI
import com.example.newsexample.ui.theme.NewsExampleTheme

@Composable
fun RouteScreen(
    modifier: Modifier = Modifier
){
    val navHostController = rememberNavController()
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                title = currentRoute,
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            BottomAppBarUI(
                route = currentRoute,
                navHostController = navHostController
            )
        }

    ){ innerPadding ->
        NavigationHostUI(
            modifier=Modifier.padding(innerPadding),
            navController = navHostController
        )
    }
}

@AppPreview
@Composable
private fun Preview(){
    NewsExampleTheme {
        RouteScreen()
    }
}