package com.example.mindmap.navGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mindmap.HomeScreen
import com.example.mindmap.model.Routes
import com.example.mindmap.uicomposable.EmotionWeeklyScreen
import com.example.mindmap.uicomposable.SelfCheckResultScreen
import com.example.mindmap.uicomposable.SelfCheckScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Routes.Home.route) {
        composable(route = Routes.Home.route) {
            HomeScreen(
                onNavigateInfo = { navController.navigate(Routes.Info.route)},
                onNavigatePost = {navController.navigate(Routes.PostNav.route)},
                onNavigateSelfCheck = { navController.navigate(Routes.SelfCheckScreen.route) },
                onNavigateRecord = { navController.navigate(Routes.EmotionWeeklyScreen.route) }
            )
        }
        InfoNavGraph(navController)
        PostNavGraph(navController)

        composable(route = Routes.SelfCheckScreen.route) {
            SelfCheckScreen(navController) { score ->
                navController.navigate("${Routes.SelfCheckResultScreen.route}/$score")
            }
        }
        composable("${Routes.SelfCheckResultScreen.route}/{score}") { backStack ->
            val score = backStack.arguments?.getString("score")?.toIntOrNull() ?: 0
            SelfCheckResultScreen(score, navController)
        }
        composable(Routes.EmotionWeeklyScreen.route) {
            EmotionWeeklyScreen(navController)
        }

    }
}