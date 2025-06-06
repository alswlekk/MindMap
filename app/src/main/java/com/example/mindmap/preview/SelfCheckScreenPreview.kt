@file:Suppress("FunctionName")

package com.example.mindmap.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mindmap.uicomposable.SelfCheckScreen

@Preview(showBackground = true)
@Composable
fun SelfCheckScreenPreview() {
    val navController = rememberNavController()
    SelfCheckScreen(navController = navController)
}