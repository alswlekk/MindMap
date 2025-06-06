package com.example.mindmap.uicomposable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mindmap.R
import com.example.mindmap.navigation.Routes

@Composable
fun HomeScreen(navController: NavController) {
    val buttonModifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(vertical = 10.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home", fontSize = 80.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(40.dp))
        Text("오늘 하루는 어떠셨나요?", fontSize = 30.sp, fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(24.dp))

        GreenIconButton(icon = Icons.Default.Edit, label = "자가진단 시작하기") {
            navController.navigate(Routes.CHECK)
        }

        GreenIconButton(drawableResId = R.drawable.baseline_history_24, label = "내 기록 보기") {
            navController.navigate(Routes.EMOTION_WEEKLY)
        }

        GreenIconButton(icon = Icons.Default.Place, label = "주변 상담소 찾기") {
            // TODO: 지도 API 화면으로 연결 필요
        }

        GreenIconButton(icon = Icons.Default.Info, label = "정신건강 정보") {
            // TODO: 정신건강 정보 화면 연결 필요
        }

        GreenIconButton(icon = Icons.Default.Person, label = "소통 게시판") {
            // TODO: 게시판 화면 연결 필요
            // label 변경
        }
    }
}

@Composable
fun GreenIconButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD8F8D4)),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                icon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
            )
            Text(
                label,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun GreenIconButton(drawableResId: Int, label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD8F8D4)),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                painter = painterResource(id = drawableResId),
                contentDescription = label,
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
            )
            Text(
                text = label,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
