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
import com.example.mindmap.ui.theme.MindMapTheme
import com.naver.maps.map.NaverMapSdk

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                MindMapTheme {
                MainScreen()
            }
        }

        val clientId = BuildConfig.NAVER_CLIENT_ID
        if (clientId.isNotEmpty()) {
            // 네이버 지도 SDK 클라이언트 설정
            NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(clientId)
        } else {
            Log.e("MainActivity", "Naver Map Client ID가 설정되지 않았습니다!")

        }
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