package com.example.mindmap.info.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepressionTipsScreen(modifier: Modifier = Modifier, navController: NavController) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "우울증 해소 팁",
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            item {
                TipsCard(
                    title = "1. 🧘‍♀️규칙적인 생활 유지 ",
                    description = "• 일정한 시간에 기상하고 취침하세요." +"\n"+
                            "• 리듬 있는 하루는 뇌의 안정감을 돕습니다."
                )
            }

            item {
                TipsCard(
                    title = "2. 🚶‍♀️가벼운 신체활동 ",
                    description = "• 매일 10 ~ 30분 산책, 스트레칭, 요가 등의 가벼운 운동을 해보세요." +"\n"+
                            "• 운동은 엔드로핀을 자극해 기분을 개선해줍니다."
                )
            }

            item {
                TipsCard(
                    title = "3. ☀️햇빛 쬐기 ",
                    description = "• 햇빛은 우울감을 줄이는데 도움이 됩니다." +"\n"+
                            "• 비타민 D부족은 우울증과 관련이 있습니다."
                )
            }

            item {
                TipsCard(
                    title = "4. 📖감정 일기 쓰기 ",
                    description = "• 하루 중 느꼈던 감정과 생각을 정리해보세요." +"\n"+
                            "• 생각의 왜곡을 파악하고 감정 조절에 도움을 줍니다."
                )
            }

            item {
                TipsCard(
                    title = "5. 💬믿을 수 있는 사람과 대화 ",
                    description = "• 대화만으로도 위로받고 감정이 가벼워질 수 있습니다."
                )
            }

            item {
                TipsCard(
                    title = "6. 🍽️영양 섭취에 신경쓰기 ",
                    description = "• 정제 탄수화물(과자, 탄산음료)은 줄이고 단백질, 비타민, 오메가-3가 풍부한 음식을 섭취해 보세요."
                )
            }
        }
    }
}