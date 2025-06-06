package com.example.mindmap.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mindmap.model.SelfCheckRecord
import com.example.mindmap.uicomposable.EmotionWeeklyScreenContent

@Preview(showBackground = true)
@Composable
fun EmotionWeeklyScreenPreview() {
    val dummyRecords = listOf(
        SelfCheckRecord(1, "2025-05-20", 7, "마음이 조금 가라앉았어요"),
        SelfCheckRecord(2, "2025-05-21", 11, "회사 일 때문에 스트레스를 많이 받았어요"),
        SelfCheckRecord(3, "2025-05-22", 4, "햇빛을 쬐며 산책해서 기분이 좋았어요"),
        SelfCheckRecord(4, "2025-05-23", 20, "무기력하고 아무것도 하기 싫은 하루였어요"),
        SelfCheckRecord(5, "2025-05-23", 14, "오늘은 많이 우울했어요..."),
        SelfCheckRecord(6, "2025-05-24", 6, "평범한 하루였어요"),
        SelfCheckRecord(7, "2025-05-25", 10, "과제 때문에 피곤해요"),
        SelfCheckRecord(8, "2025-05-25", 24, "하루 종일 울고 싶었어요"),
        SelfCheckRecord(9, "2025-05-26", 2, "친구랑 대화해서 즐거웠어요")
    )

    EmotionWeeklyScreenContent(
        navController = rememberNavController(),
        allRecords = dummyRecords,
        graphRecords = dummyRecords.distinctBy { it.date }
    )
}