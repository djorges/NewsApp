package com.example.newsexample.ui.composable

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.savedstate.savedState

@Composable
fun BottomAppBarUI(
    route: String?,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
){
    NavigationBar (modifier = modifier){
        BottomAppBarItem().bottomAppBarItems().forEach {item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                selected = item.route == route,
                label = { Text(text = item.title)  },
                onClick = {
                    //TODO: Delete navhostcontroller
                    navHostController.navigate(item.route){
                        launchSingleTop = true
                        savedState { true }
                    }
                }
            )
        }

    }
}


data class BottomAppBarItem(
    val icon: ImageVector = Icons.Default.Home,
    val title: String = "",
    val route: String = ""
){
    fun bottomAppBarItems():List<BottomAppBarItem>{
        return listOf(
            BottomAppBarItem(
                icon = Icons.Default.Home,
                title = "Home Screen",
                route = Screens.HomeScreen.route
            ),
            BottomAppBarItem(
                icon = Icons.Default.Search,
                title = "Search Screen",
                route = Screens.SearchScreen.route
            ),
            BottomAppBarItem(
                icon = Icons.Default.Favorite,
                title = "Saved Screen",
                route = Screens.SavedScreen.route
            )
        )
    }
}
