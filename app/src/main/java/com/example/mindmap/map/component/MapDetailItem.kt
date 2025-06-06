package com.example.mindmap.map.component

import android.R.attr.font
import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MapDetailItem(image: Int, icName: String,modifier: Modifier = Modifier,onClickEvent: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable { onClickEvent() }
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = modifier
                .size(30.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = icName,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}