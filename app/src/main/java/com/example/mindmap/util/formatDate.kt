package com.example.mindmap.util

import android.icu.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dataString: String): String {
    return  try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy년 M월 d일", Locale.getDefault())
        val date = inputFormat.parse(dataString)
        if (date != null) outputFormat.format(date) else dataString
    } catch (e: Exception) {
        dataString
    }
}