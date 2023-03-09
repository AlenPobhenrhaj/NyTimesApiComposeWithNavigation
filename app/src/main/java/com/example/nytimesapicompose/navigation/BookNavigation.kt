package com.example.nytimesapicompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nytimesapicompose.ui.theme.BookDetails
import com.example.nytimesapicompose.ui.theme.Screen


@Composable
fun BookNavigation()
{
    val navController = rememberNavController()
    NavHost(navController, startDestination = "screen") {
        composable("screen") { Screen(navController) }
        composable("bookDetailsPage/{title}/{description}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val description = backStackEntry.arguments?.getString("description") ?: ""
            BookDetails(title, description) {
                navController.navigateUp()
            }
        }

    }
}

