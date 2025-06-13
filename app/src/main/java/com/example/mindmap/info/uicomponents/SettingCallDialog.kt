package com.example.mindmap.info.uicomponents

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun SettingsCallDialog(
    onDismiss: () -> Unit,
    onGoToSettings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "권한 필요",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        text = {
            Text(
                "전화 기능을 사용하려면 앱 설정에서 권한을 허용해 주세요.",
                color = Color.Black)
        },
        confirmButton = {
            Button(
                onClick = onGoToSettings,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFCFF7D3)
                )
            )
            {
                Text(
                    "설정으로 이동",
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
            )
            {
                Text(
                    "취소",
                    color = Color.Black
                )
            }
        },
        containerColor = Color.White
    )
}
