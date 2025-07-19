package com.example.thmanyahaudiotask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thmanyahaudiotask.ui.home.views.HomeScreen
import com.example.thmanyahaudiotask.ui.search.view.SearchScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.HOME.name
    ) {
        composable(Screens.SEARCH.name) {
            SearchScreen(
                navController
            )
        }

        composable(Screens.HOME.name) {
            HomeScreen(
                navController
            )
        }

    }
}