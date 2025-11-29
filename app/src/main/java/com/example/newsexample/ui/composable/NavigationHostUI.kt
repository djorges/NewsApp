package com.example.newsexample.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsexample.ui.screen.HomeScreen
import com.example.newsexample.ui.screen.SavedScreen
import com.example.newsexample.ui.screen.SearchScreen
import com.example.newsexample.ui.viewmodel.NewsViewModel

@Composable
fun NavigationHostUI(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
        modifier = modifier
    ){
        composable(Screens.HomeScreen.route){
            HomeScreen(viewModel = viewModel)
        }
        composable (Screens.SearchScreen.route){
            SearchScreen(viewModel = viewModel)
        }
        composable (Screens.SavedScreen.route){
            SavedScreen()
        }
    }
}