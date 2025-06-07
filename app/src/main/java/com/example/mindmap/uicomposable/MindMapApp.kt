package com.example.mindmap.uicomposable

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mindmap.navigation.Routes

@Composable
fun MindMapApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen(navController) }
        composable(Routes.CHECK) {
            SelfCheckScreen(navController) { score ->
                navController.navigate("${Routes.RESULT}/$score")
            }
        }
        composable("${Routes.RESULT}/{score}") { backStack ->
            val score = backStack.arguments?.getString("score")?.toIntOrNull() ?: 0
            SelfCheckResultScreen(score, navController)
        }
        composable(Routes.EMOTION_WEEKLY) {
            EmotionWeeklyScreen(navController)
        }
    }
}
