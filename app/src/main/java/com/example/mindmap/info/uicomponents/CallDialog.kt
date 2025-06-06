package com.example.mindmap.info.uicomponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CallDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    tel:String,
    title:String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "전화 걸기 확인",
                fontWeight = FontWeight.Bold
            )
        },
        text = { Text("지금 $title 번호($tel)로 전화를 거시겠습니까?") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCFF7D3)
                )
            ) {
                Text(
                    "전화 걸기",
                    color = Color.Black
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCFF7D3)
                )
            ) {
                Text(
                    "취소",
                    color = Color.Black,

                    )
            }
        },
        containerColor = Color.White
    )
}

