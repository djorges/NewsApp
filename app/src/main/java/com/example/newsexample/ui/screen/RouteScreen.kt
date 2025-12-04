package com.example.newsexample.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsexample.AppPreview
import com.example.newsexample.ui.composable.BottomAppBarUI
import com.example.newsexample.ui.composable.CustomTopAppBar
import com.example.newsexample.ui.composable.NavigationHostUI
import com.example.newsexample.ui.composable.Screens
import com.example.newsexample.ui.theme.NewsExampleTheme
import com.example.newsexample.ui.viewmodel.NewsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RouteScreen(
    modifier: Modifier = Modifier
){
    val navHostController = rememberNavController()
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val viewModel: NewsViewModel = koinViewModel()
    val snackbarHostState = viewModel.snackbarHostState


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if(currentRoute == Screens.SearchScreen.route || currentRoute == Screens.HomeScreen.route){
                CustomTopAppBar(
                    title = currentRoute,
                    modifier = Modifier.fillMaxWidth(),
                    onUserSearchQuery = {
                        viewModel.getSearchNews(it)
                    },
                    onSearchIconClicked = {
                        navHostController.navigate(Screens.SearchScreen.route)
                    }
                )
            }
        },
        bottomBar = {
            BottomAppBarUI(
                route = currentRoute,
                navHostController = navHostController
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ){ innerPadding ->
        NavigationHostUI(
            modifier=Modifier.padding(innerPadding),
            navController = navHostController,
            viewModel = viewModel
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