package com.example.mindmap.info.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindmap.R

@Composable
fun Tips(
    modifier: Modifier = Modifier,
    title: String, description: String
) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.outline_check_box_24),
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(title, fontWeight = FontWeight.Bold,
                fontSize = 20.sp
               )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                description,
                fontSize = 20.sp
            )
        }
    }
}