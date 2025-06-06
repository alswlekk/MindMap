package com.example.mindmap

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mindmap.navGraph.NavGraph

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavGraph(navController = navController)
}