package com.example.mindmap.map.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindmap.R

@Composable
fun MapTopAppBar(modifier: Modifier = Modifier,
                 title: String,
                 onBackClick: () -> Unit
                 ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 21.dp),
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.img_left_arrow),
                contentDescription = null,
            )
        }
        Text(
            text = title,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }

}

@Preview
@Composable
fun MapTopAppBarPreview(modifier: Modifier = Modifier) {
    MapTopAppBar(
        title = "병원 찾기"
    ) {}
}