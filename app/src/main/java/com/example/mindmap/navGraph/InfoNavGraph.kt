package com.example.mindmap.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mindmap.info.uicomponents.CounselingTipsScreen
import com.example.mindmap.info.uicomponents.DepressionTipsScreen
import com.example.mindmap.info.uicomponents.EmergencyCallScreen
import com.example.mindmap.info.uicomponents.InfoHomeScreen
import com.example.mindmap.model.Routes

fun NavGraphBuilder.InfoNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.InfoHomeScreen.route,
        route = Routes.Info.route
    ) {
        composable(route = Routes.InfoHomeScreen.route) {
            InfoHomeScreen(
                navController = navController,
                onNavigateCounsel = { navController.navigate(Routes.CounselingTipsScreen.route) },
                onNavigateDeperssion = { navController.navigate(Routes.DepressionTipsScreen.route) },
                onNavigateEmergency = { navController.navigate(Routes.EmergencyCallScreen.route) }
            )
        }

        composable(route = Routes.CounselingTipsScreen.route) {
            CounselingTipsScreen(navController = navController)
        }

        composable(route = Routes.DepressionTipsScreen.route) {
            DepressionTipsScreen(navController = navController)
        }

        composable(route = Routes.EmergencyCallScreen.route) {
            EmergencyCallScreen(navController = navController)
        }
    }
}