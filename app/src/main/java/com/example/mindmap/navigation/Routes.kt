package com.example.mindmap.navigation

sealed class Routes(val route: String) {
    object Post : Routes("PostList")
    object NewPost : Routes("NewPost")
    object PostDetail : Routes("PostDetail")
}