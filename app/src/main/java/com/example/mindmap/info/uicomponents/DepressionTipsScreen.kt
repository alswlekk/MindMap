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
import com.example.mindmap.info.ui.TipsCard

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
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.spacedBy(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            item {
                TipsCard(
                    title = "1. 🧘‍♀️규칙적인 생활 유지 ",
                    description = "• 리듬 있는 하루는 뇌의 안정감을 돕습니다." +"\n"+
                            "• 실현 가능한 일상 계획표를 설정하고 실행해 보세요."
                )
            }

            item {
                TipsCard(
                    title = "2. 🛌충분한 수면 취하기 ",
                    description = "• 일정한 시간에 기상하고 취침하세요."+"\n"+
                    "• 컴퓨터나 TV, 휴대 전화, 태블릿 PC의 푸른빛과 자극은 수면을 방해하니 수면 전 최대한 사용을 피하세요. "
                )
            }

            item {
                TipsCard(
                    title = "3. 🚶‍♀️가벼운 신체 활동하기 ",
                    description = "• 매일 10 ~ 30분 산책, 스트레칭, 요가 등의 가벼운 운동을 해보세요." +"\n"+
                            "• 운동은 엔도로핀을 자극해 기분을 개선해 줍니다."
                )
            }

            item {
                TipsCard(
                    title = "4. ☀️햇빛 쬐기 ",
                    description = "• 햇빛은 우울감을 줄이는 데 도움이 됩니다." +"\n"+
                            "• 비타민D 부족은 우울증과 관련이 있습니다."
                )
            }

            item {
                TipsCard(
                    title = "5. 📖감정 일기 쓰기 ",
                    description = "• 하루 중 느꼈던 감정과 생각을 정리해 보세요." +"\n"+
                            "• 생각의 왜곡을 파악하고 감정 조절에 도움을 줍니다."
                )
            }

            item {
                TipsCard(
                    title = "6. 💬믿을 수 있는 사람과 대화하기 ",
                    description = "• 대화만으로도 위로받고 감정이 가벼워질 수 있습니다."
                )
            }

            item {
                TipsCard(
                    title = "7. 🆕새로운 것을 시도하기 ",
                    description = "• 박물관에 가기, 새로운 언어 배우기등 색다른 것에 시도하세요."+"\n"+
                            "• 새로운 것을 도전하려고 할 때 도파민의 분비가 증가해 기쁨, 즐거움으로 이어지게 됩니다."
                )
            }

            item {
                TipsCard(
                    title = "8. 🍽️영양 섭취에 신경 쓰기 ",
                    description = "• 정제 탄수화물(과자, 탄산음료)은 줄이고 단백질, 비타민, 오메가-3가 풍부한 음식을 섭취해 보세요."
                )
            }

            item {
                TipsCard(
                    title = "9. 🍺알코올 및 마약성 약물 피하기 ",
                    description = "• 알코올이나 마약성 약물은 우울증 증상을 완화시키는 것처럼 보이지만 장기적으로는 증상을 악화시키고 우울증을 치료하기 어렵게 만듭니다."
                )
            }

            item {
                TipsCard(
                    title = "10. 💊약물치료는 꾸준히 이어가기  ",
                    description = "• 약을 복용 중이라면 기분이 나아지더라도 약을 건너뛰지 마세요."+"\n"+
                    "• 중단 시 우울증 증상이 다시 나타날 수 있으며 금단 증상이 나타날 수도 있습니다."
                )
            }


        }
    }
}