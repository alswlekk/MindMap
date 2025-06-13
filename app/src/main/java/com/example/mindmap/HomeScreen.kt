package com.example.mindmap

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigatePost: () -> Unit,
    onNavigateInfo: () -> Unit,
    onNavigateSelfCheck: () -> Unit,
    onNavigateRecord: () -> Unit
) {
    val buttonModifier = Modifier
        .width(350.dp)
        .height(60.dp)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Home",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "오늘 하루는 어떠셨나요?",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(40.dp))


            Button(
                onClick = {
                    onNavigateSelfCheck()
                },
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCFF7D3)
                ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_checklist_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "자가진단 시작하기",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    onNavigateRecord()
                },
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCFF7D3)
                ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_book_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("내 기록 보기",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {

                },
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCFF7D3)
                ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_location_on_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("주변 상담소 찾기",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    onNavigateInfo()

                },
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCFF7D3)
                ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_info_outline_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("정신건강 정보",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    onNavigatePost()
                },
                modifier = buttonModifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCFF7D3)
                ),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_people_outline_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("소통 게시판",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}
