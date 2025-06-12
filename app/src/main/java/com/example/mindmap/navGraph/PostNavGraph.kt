package com.example.mindmap.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.mindmap.Post.View.NewPostView
import com.example.mindmap.Post.View.PostDetailView
import com.example.mindmap.Post.View.PostView
import com.example.mindmap.model.Routes

fun NavGraphBuilder.PostNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.Post.route,
        route = Routes.PostNav.route
    ) {
        // Post List
        composable(route = Routes.Post.route) {
            PostView(
                onNewPostNavigate = { navController.navigate(Routes.NewPost.route) },
                onPostDetailNavigate = { postKey ->
                    navController.navigate(Routes.PostDetail.route + "/$postKey")
                },
                onHomeNavigate = { navController.popBackStack() }
            )
        }
        // NewPost
        composable(
            route = Routes.NewPost.route
        ) {
            NewPostView(
                onPostNavigate = { navController.popBackStack() }
            )
        }
        // PostDetail
        composable(
            route = Routes.PostDetail.route + "/{postKey}",
            arguments = listOf(
                navArgument(name = "postKey") {
                    type = NavType.StringType
                }
            )
        ) {
            PostDetailView(
                postKey = it.arguments?.getString("postKey"),
                onPostNavigate = { navController.popBackStack() }
            )
        }
    }
}