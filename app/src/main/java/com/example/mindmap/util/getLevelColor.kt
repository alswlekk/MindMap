package com.example.mindmap.util

import androidx.compose.ui.graphics.Color

fun getLevelColor(level: String): Color {
    return when (level) {
        "정상 범위" -> Color(0xFF4CAF50)
        "가벼운 우울" -> Color(0xFFFFC107)
        "중간 정도의 우울" -> Color(0xFFFF9800)
        "심한 우울" -> Color(0xFFF44336)
        else -> Color.Black
    }
}