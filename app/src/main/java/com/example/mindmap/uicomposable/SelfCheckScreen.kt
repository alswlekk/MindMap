package com.example.mindmap.uicomposable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mindmap.navigation.Routes
import com.example.mindmap.viewmodel.CheckSessionViewModel

@Composable
fun SelfCheckScreen(
    navController: NavController,
    viewModel: CheckSessionViewModel = viewModel(),
    onSubmit: (Int) -> Unit = { score ->
        viewModel.submitAnswers(score)
        navController.navigate("${Routes.RESULT}/$score") 
    }
) {
    val context = LocalContext.current
    val answers = viewModel.answers
    val isAllAnswered = answers.none { it == null }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            viewModel.shuffledQuestions.forEachIndexed { index, pair ->
                QuestionCard(
                    number = index + 1,
                    question = pair.second,
                    selectedOption = answers[index],
                    onOptionSelected = { answers[index] = it }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Button(
                onClick = {
                    if(isAllAnswered) {
                        onSubmit(viewModel.getTotalScore())
                    }
                },
                enabled = isAllAnswered,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAllAnswered) Color(0xFF4CAF50) else Color(0xFFBDBDBD),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            ) {
                Text("완료하기")
            }
        }
    }
}

@Composable
fun QuestionCard(
    number: Int,
    question: String,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit
) {
    val options = listOf("없음", "2~6일", "7~12일", "거의 매일")
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD8F8D4))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("$number. $question", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            for (rowIndex in 0 until options.size step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    (0..1).forEach { offset ->
                        val optionIndex = rowIndex + offset
                        if (optionIndex < options.size) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                RadioButton(
                                    selected = selectedOption == optionIndex,
                                    onClick = { onOptionSelected(optionIndex) }
                                )
                                Text(text = options[optionIndex])
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}
