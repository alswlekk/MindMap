package com.example.mindmap.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mindmap.map.data.FacilityType
import com.example.mindmap.map.screen.MapDetailScreen
import com.example.mindmap.map.screen.MapScreen
import com.naver.maps.geometry.LatLng

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Map.route
    ) {
        composable(Routes.Map.route) {
            MapScreen(
                onBack = {
                    navController.navigateUp()
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
                    navController.navigateUp()
                },
            )
        }
    }
}