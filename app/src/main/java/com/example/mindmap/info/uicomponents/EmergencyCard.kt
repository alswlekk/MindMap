package com.example.mindmap.info.uicomponents

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.net.toUri

@Composable
fun EmergencyCard(
    modifier: Modifier = Modifier,
    title: String,
    tel: String,
    permission: String,
    onGranted: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity

    var showCallDialog by remember { mutableStateOf(false) }
    var showSettingDialog by remember { mutableStateOf(false) }
    var showSettingCheck by remember { mutableStateOf(false) }

    var showCallConfirmDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onGranted()
        } else {
            if (shouldShowRequestPermissionRationale(activity, permission)) {
                showCallDialog = true
            } else {
                showSettingCheck = true
            }
        }
    }

    fun requestPermission() {
        when {
            checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
                onGranted()
            }
            shouldShowRequestPermissionRationale(activity, permission) -> {
                showCallDialog = true
            }
            showSettingCheck -> {
                showSettingDialog = true
            }
            else -> {
                launcher.launch(permission)
            }
        }
    }

    if (showCallDialog) {
        RationaleCallDialog(
            onDismiss = { showCallDialog = false },
            onConfirm = {
                showCallDialog = false
                launcher.launch(permission)
            }
        )
    }

    if (showSettingDialog) {
        SettingsCallDialog(
            onDismiss = { showSettingDialog = false },
            onGoToSettings = {
                showSettingDialog = false
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = "package:${context.packageName}".toUri()
                }
                context.startActivity(intent)
            }
        )
    }

    if (showCallConfirmDialog) {
        CallDialog(
            title = title,
            tel = tel,
            onDismiss = { showCallConfirmDialog = false },
            onConfirm = {
                showCallConfirmDialog = false
                requestPermission()
            }
        )
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFCFF7D3)
        ),
        onClick = { showCallConfirmDialog = true },
        modifier = modifier.width(350.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                tel,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
