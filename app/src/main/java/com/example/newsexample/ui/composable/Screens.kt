package com.example.newsexample.ui.composable

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home")
    object SearchScreen : Screens("search")
    object SavedScreen : Screens("saved")
    object DetailsScreen : Screens("details/{article}") {
        fun passArticle(articleJson: String) = "details/$articleJson"
    }
}