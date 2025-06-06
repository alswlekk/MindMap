package com.example.mindmap.info.uicomponents

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.week10.functions.makeCall


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyCallScreen(modifier: Modifier = Modifier, navController: NavController) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "긴급 연락처",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmergencyCard(
                title = "자살예방상담",
                tel = "109",
                permission = Manifest.permission.CALL_PHONE,
                onGranted = { makeCall("109",context) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            EmergencyCard(
                title = "한국생명의전화",
                tel = "1588-9191",
                permission = Manifest.permission.CALL_PHONE,
                onGranted = { makeCall("1588-9191",context) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            EmergencyCard(
                title = "정신건강상담전화",
                tel = "1577-0199",
                permission = Manifest.permission.CALL_PHONE,
                onGranted = { makeCall("1577-0199",context) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            EmergencyCard(

                title = "보건복지상담센터",
                tel = "129",
                permission = Manifest.permission.CALL_PHONE,
                onGranted = { makeCall("129",context) }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "24시간 상담 가능, 익명 상담 보장",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
        }
    }
}