package com.example.week10.functions

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun makeCall(tel:String, context: Context) {
    val number = "tel:$tel".toUri()
    val callIntent = Intent(Intent.ACTION_CALL, number)
    context.startActivity(callIntent)
}