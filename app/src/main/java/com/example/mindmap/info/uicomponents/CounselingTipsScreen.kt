package com.example.mindmap.info.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mindmap.info.ui.Tips
import com.example.mindmap.info.ui.TipsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounselingTipsScreen(modifier: Modifier = Modifier, navController: NavController) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "상담 준비 팁",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .background(color = Color.White)
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFCFF7D3)),
                    modifier = Modifier.width(350.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Tips(
                            title = "요즘 감정 메모해 오기",
                            description = "하고 싶은말, 고민을 간단히 정리해 보세요."
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Tips(
                            title = "10분 일찍 도착하기",
                            description = "서두르지 않으면 마음도 더 편해져요."
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Tips(
                            title = "심리검사 기록이 있으면 챙기기",
                            description = "있다면 상담에 도움을 줄 수 있어요."
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Tips(
                            title = "센터 위치, 연락처 확인",
                            description = "당일 헷갈리지 않게 미리 확인해요."
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Tips(
                            title = "긴장하지 않기로 마음먹기",
                            description = "잘 말하려 애쓰지 않아도 괜찮아요."
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Tips(
                            title = "카페인/자극적인 뉴스 피하기",
                            description = "마음이 차분하면 상담도 더 편해져요."
                        )
                    }
                }
            }
        }
    }
}
