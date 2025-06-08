package com.example.mindmap

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mindmap.navigation.NavGraph
import com.example.mindmap.ui.theme.MindMapTheme
import com.naver.maps.map.NaverMapSdk

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MindMapTheme {
//                NaverMapScreen02()
//                NaverMapScreen()
//                MapScreen()
                MindMapTheme {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }

        val clientId = applicationContext
            .packageManager
            .getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            .metaData
            .getString("com.naver.maps.CLIENT_ID")

        Log.d("NAVER_CLIENT_ID", "Loaded: $clientId")
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(clientId!!)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MindMapTheme {
        Greeting("Android")
    }
}