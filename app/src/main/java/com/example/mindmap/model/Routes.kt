package com.example.mindmap.model

sealed class Routes(val route: String) {
    object Home : Routes(route = "Home")
    object Info : Routes("Info")
    object InfoHomeScreen: Routes(route = "InfoHome")
    object CounselingTipsScreen: Routes(route = "Counsel")
    object DepressionTipsScreen: Routes(route = "Depression")
    object EmergencyCallScreen: Routes(route = "Emergency")
    object SelfCheckScreen: Routes(route = "check")
    object SelfCheckResultScreen: Routes(route = "result")
    object EmotionWeeklyScreen: Routes(route = "emotion_weekly")

}