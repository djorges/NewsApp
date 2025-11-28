package com.example.newsexample.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsexample.ui.screen.HomeScreen
import com.example.newsexample.ui.screen.SavedScreen
import com.example.newsexample.ui.screen.SearchScreen

@Composable
fun NavigationHostUI(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route,
        modifier = modifier
    ){
        composable(Screens.HomeScreen.route){
            HomeScreen()
        }
        composable (Screens.SearchScreen.route){
            SearchScreen()
        }
        composable (Screens.SavedScreen.route){
            SavedScreen()
        }
    }
}