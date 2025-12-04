package com.example.newsexample.ui.composable

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsexample.data.api.Article
import com.example.newsexample.data.api.Source
import com.example.newsexample.ui.screen.DetailScreen
import com.example.newsexample.ui.screen.HomeScreen
import com.example.newsexample.ui.screen.SavedScreen
import com.example.newsexample.ui.screen.SearchScreen
import com.example.newsexample.ui.viewmodel.NewsViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
            HomeScreen(viewModel = viewModel, onItemClick = {article ->
                val articleJson = Uri.encode(Json.encodeToString(article))
                navController.navigate(Screens.DetailsScreen.passArticle(articleJson))
            })
        }
        composable (Screens.SearchScreen.route){
            SearchScreen(viewModel = viewModel)
        }
        composable(
            route =Screens.DetailsScreen.route,
            arguments = listOf(navArgument("article") { type = NavType.StringType })
        ){ backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("article")!!
            val article = Json.decodeFromString<Article>(articleJson)

            DetailScreen(
                article = article
            )
        }

        composable (Screens.SavedScreen.route){
            SavedScreen()
        }
    }
}