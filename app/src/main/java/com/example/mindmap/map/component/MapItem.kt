package com.example.mindmap.map.component

import android.R.attr.fontWeight
import android.R.attr.name
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindmap.R
import com.example.mindmap.map.data.FacilityType

@Composable
fun MapItem(facilityType: FacilityType, name:String, modifier: Modifier = Modifier, onClickListener : () -> Unit = {}) { // 시설 정보를 파라미터로 받도록 수정 필요
    Box(modifier = Modifier.fillMaxWidth()
        .background(
            color = Color(0xFFCFF7D3),
            shape = RoundedCornerShape(20.dp),
        )
        .padding(vertical = 14.dp)
        .clickable {
            onClickListener() // 클릭 이벤트 처리
        }
        ,
    ) {
        when (facilityType) {
            FacilityType.COUNSELING_CENTER -> {
                // 상담센터 아이템
                Row(
                    modifier = modifier
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 상담센터 아이템 UI 구성
                    Image(
                        painter = painterResource(R.drawable.ic_marker_center),
                        contentDescription = null,
                        modifier = Modifier
                            .size(38.dp)
                    )
                    Spacer(modifier = Modifier.size(7.dp))
                    Column {
                        Text(text = "심리상담센터",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold)
                        Text(text = name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold)
                    }
                }
            }

            FacilityType.PSYCHIATRIC_HOSPITAL -> {
                // 정신과 병원 아이템
                Row(
                    modifier = modifier
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 상담센터 아이템 UI 구성
                    Image(
                        painter = painterResource(R.drawable.ic_marker_hospital),
                        contentDescription = null,
                        modifier = Modifier
                            .size(38.dp)
                    )
                    Spacer(modifier = Modifier.size(7.dp))
                    Column {
                        Text(
                            text = "정신건강의학과",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = name.replace("+", " "),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            }
        }
}

@Preview
@Composable
private fun MapItemPreview() {
    Column {
        MapItem(
            facilityType = FacilityType.COUNSELING_CENTER,
            name = "OO센터"
        )
        Spacer(modifier = Modifier.size(10.dp))
        MapItem(
            facilityType = FacilityType.PSYCHIATRIC_HOSPITAL,
            name = "OO병원"
        )
    }
}
