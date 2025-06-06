@file:Suppress("FunctionName")

package com.example.mindmap.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mindmap.uicomposable.SelfCheckResultScreen

@Preview(showBackground = true)
@Composable
fun SelfCheckResultScreenPreview() {
    val dummyScore = 10
    val navController = rememberNavController()

    SelfCheckResultScreen(
        score = dummyScore,
        navController = navController
    )
}