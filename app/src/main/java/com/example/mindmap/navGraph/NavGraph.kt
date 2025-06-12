package com.example.mindmap.navGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mindmap.HomeScreen
import com.example.mindmap.map.data.FacilityType
import com.example.mindmap.map.screen.MapDetailScreen
import com.example.mindmap.map.screen.MapScreen
import com.example.mindmap.model.Routes
import com.example.mindmap.uicomposable.EmotionWeeklyScreen
import com.example.mindmap.uicomposable.SelfCheckResultScreen
import com.example.mindmap.uicomposable.SelfCheckScreen
import com.naver.maps.geometry.LatLng


@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Routes.Home.route) {

        composable(route = Routes.Home.route) {
            HomeScreen(
                onNavigateInfo = { navController.navigate(Routes.Info.route)},
                onNavigateSelfCheck = { navController.navigate(Routes.SelfCheckScreen.route) },
                onNavigateRecord = { navController.navigate(Routes.EmotionWeeklyScreen.route) },
                onNavigateMap = {navController.navigate(Routes.Map.route)}
            )
        }

        InfoNavGraph(navController)

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

        composable(Routes.Map.route) {
            MapScreen(
                onBack = {
                    navController.popBackStack()
                },
                navController = navController,
            )
        }
        composable(Routes.MapDetail.route) { backStackEntry ->
            val facilityType = backStackEntry.arguments?.getString("facilityType") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val address = backStackEntry.arguments?.getString("address") ?: ""
            val phone = backStackEntry.arguments?.getString("phone") ?: ""
            val locationStr = backStackEntry.arguments?.getString("location") ?: "0.0, 0.0"
            val website = backStackEntry.arguments?.getString("website")

            // locationStr을 LatLng로 변환
            val locationParts = locationStr.split(",")
            val location = LatLng(locationParts[0].toDouble(), locationParts[1].toDouble())

            MapDetailScreen(
                facilityType = FacilityType.valueOf(facilityType), // FacilityType은 enum을 사용한다고 가정
                name = name,
                address = address,
                phone = phone,
                location = location,
                website = website,
                onBack = {
                    navController.popBackStack()
                },
            )
        }

    }
}