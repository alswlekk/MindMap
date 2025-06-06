package com.example.mindmap.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mindmap.View.NewPostView
import com.example.mindmap.View.PostDetailView
import com.example.mindmap.View.PostView

@Composable
fun PostNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Post.route) {
        //Post
        composable(route = Routes.Post.route) {
            PostView(
                onNewPostNavigate = {
                    navController.navigate(Routes.NewPost.route)
                },
                onPostDetailNavigate = { postKey ->
                    navController.navigate(Routes.PostDetail.route + "/$postKey")
                }
            )
        }
        //NewPost
        composable(
            route = Routes.NewPost.route
        ) {
            NewPostView(
                onPostNavigate = {
                    navController.navigate(Routes.Post.route)
                }
            )
        }
        //PostDetail
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
                onPostNavigate = {
                    navController.navigate(Routes.Post.route)
                }
            )
        }
    }
}