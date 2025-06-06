package com.example.mindmap.navGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mindmap.HomeScreen
import com.example.mindmap.model.Routes

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Routes.Home.route) {

        composable(route = Routes.Home.route) {
            HomeScreen(
                onNavigateInfo = { navController.navigate(Routes.Info.route)},
            )
        }

        InfoNavGraph(navController)

    }
}