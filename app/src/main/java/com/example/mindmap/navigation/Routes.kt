package com.example.mindmap.navigation

import androidx.compose.ui.input.key.Key.Companion.Home

sealed class Routes (val route: String, val isRoot : Boolean = true) {
    object Map : Routes("Map")
    object MapDetail : Routes("map_detail_screen/{facilityType}/{name}/{address}/{phone}/{location}/{website}")

    companion object {
        fun getRoutes(route : String): Routes { // object 객체 반환하는 함수
            return when (route) {
                Map.route -> Map
                MapDetail.route -> MapDetail
//                else -> Home // 기본값으로 Home을 반환
                else -> MapDetail // 나중에 수정 필요
            }
        }
    }
}