package com.example.mindmap.info.uicomponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RationaleCallDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "권한 확인 요청",
                fontWeight = FontWeight.Bold
            )
        },
        text = { Text("전화 걸기 권한이 승인되어야 합니다.") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCFF7D3)
                )
            ) {
                Text(
                    "권한승인",
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