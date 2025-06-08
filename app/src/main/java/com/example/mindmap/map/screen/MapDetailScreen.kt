package com.example.mindmap.map.screen

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.mindmap.R
import com.example.mindmap.map.component.MapDetailItem
import com.example.mindmap.map.component.MapTopAppBar
import com.example.mindmap.map.data.FacilityType
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.OverlayImage


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapDetailScreen(
    facilityType: FacilityType,
    name: String,
    address: String,
    phone: String,
    location: LatLng,
    website: String? = null,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) { // 병원 정보를 파라미터로 받아줘야 함
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(location, 15.0)
    }

    val mapProperties = MapProperties(
        locationTrackingMode = LocationTrackingMode.None,
        isTrafficLayerGroupEnabled = false,
        isLiteModeEnabled = false
    )

    val mapUiSettings = MapUiSettings(
        isScrollGesturesEnabled = false,
        isZoomControlEnabled = false,
        isScaleBarEnabled = false
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 23.dp)
    ) {
        Log.d(
            "MapDetailScreen",
            "Marker position: $location, 기관 : $name, 주소: $address, 전화번호: $phone, 웹사이트: $website"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 23.dp),

            ) {
            MapTopAppBar(
                title = "병원 정보",
            ) {
                onBack()
            }
            Spacer(modifier = Modifier.size(46.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
            )
            {
                NaverMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(239.dp),
                    properties = mapProperties,
                    uiSettings = mapUiSettings,
                    cameraPositionState = cameraPositionState
                ) {
                    val markerIcon =
                        if (facilityType == FacilityType.COUNSELING_CENTER) {
                            OverlayImage.fromResource(R.drawable.ic_marker_center)
                        } else {
                            OverlayImage.fromResource(R.drawable.ic_marker_hospital)
                        }
                    Marker(
                        state = rememberMarkerState(position = location),
                        icon = markerIcon,
                    )
                }
            }
            Spacer(modifier = Modifier.size(21.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .background(
                        color = Color(0xFFCFF7D3),
                        shape = RoundedCornerShape(20.dp),
                    )
                    .padding(vertical = 29.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = name.replace("+", " "), // 병원 정보 받아서 출력하도록 수정 필요
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.size(21.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            MapDetailItem(R.drawable.img_map_icon, "길찾기", onClickEvent = {
                                val encodedQuery = Uri.encode(address.replace("+", " "))
                                val uri = Uri.parse("https://map.naver.com/v5/search/$encodedQuery")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            })
                            Spacer(modifier = Modifier.size(24.dp))
                            MapDetailItem(R.drawable.img_call_icon, "전화 걸기", onClickEvent = {
                                val phoneUri = Uri.parse("tel:${phone}")
                                val intent = Intent(Intent.ACTION_DIAL, phoneUri)
                                context.startActivity(intent)
                            })
                        }
                    }
                    Spacer(modifier = Modifier.size(31.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp),
                        verticalArrangement = Arrangement.spacedBy(22.dp)
                    ) {
                        Text(
                            text = address.replace("+", " "),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = phone, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        if (website != null && website.equals("null")
                                .not() && website.equals("www.").not()
                        )
                            Text(
                                text = website.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6480FD),
                                modifier = Modifier.clickable {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        website.toUri()
                                    )
                                    context.startActivity(intent)
                                }
                            )
                    }
                }
            }

        }
    }
}

@Preview
@Composable
private fun MapDetailScreenPreview() {
    MapDetailScreen(
        facilityType = FacilityType.COUNSELING_CENTER,
        name = "심리상담센터",
        address = "서울시 강남구 역삼동 123-45",
        phone = "02-1234-5678",
        location = LatLng(37.5408, 127.0793),
        website = "https://example.com" // 예시 URL
    )
}