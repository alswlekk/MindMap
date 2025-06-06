package com.example.mindmap.uicomposable

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mindmap.model.SelfCheckRecord
import com.example.mindmap.navigation.Routes
import com.example.mindmap.util.SelfCheckEvaluator
import com.example.mindmap.util.getLevelColor
import com.example.mindmap.viewmodel.SelfCheckViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SelfCheckResultScreen(
    score: Int,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val app = context.applicationContext as android.app.Application
            return SelfCheckViewModel(app) as T
        }
    }
    val viewModel: SelfCheckViewModel = viewModel(factory = viewModelFactory)

    val (level, message) = SelfCheckEvaluator().evaluate(score)
    var memo by remember { mutableStateOf("") }
    val isMemoNotBlank = memo.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로가기")
                }

                Text(
                    text = "자가진단",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Text("나의 점수는?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("$score 점", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                level,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = getLevelColor(level)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(message)
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD8F8D4))
            ) {
                BasicTextField(
                    value = memo,
                    onValueChange = { memo = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    textStyle = TextStyle(color = Color.Black),
                    decorationBox = { innerTextField ->
                        if (memo.isBlank()) {
                            Text("메모를 입력해주세요.", color = Color.Gray)
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                var date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val record = SelfCheckRecord(date = date, score = score, memo = memo)

                viewModel.saveRecord(record)
                Toast.makeText(context, "기록이 저장되었습니다", Toast.LENGTH_SHORT).show()
                navController.navigate(Routes.HOME) {
                    popUpTo(0)
                }
            },
            enabled = isMemoNotBlank,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isMemoNotBlank) Color(0xFF4CAF50) else Color(0xFFBDBDBD),
                contentColor = Color.White
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("완료하기")
        }
    }
}

