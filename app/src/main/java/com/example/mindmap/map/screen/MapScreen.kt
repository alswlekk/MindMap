package com.example.mindmap.map.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.mindmap.R
import com.example.mindmap.map.component.MapItem
import com.example.mindmap.map.component.MapTopAppBar
import com.example.mindmap.map.data.FacilityData
import com.example.mindmap.map.data.FacilityType
import com.example.mindmap.map.retrofit.FetchMindMedApiData
import com.example.mindmap.map.retrofit.FetchSocialDevApiData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.OverlayImage
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    navController: NavHostController
) {
    val context = LocalContext.current

    val userLatLng = remember { mutableStateOf(LatLng(37.5408, 127.0793)) }
    val cameraPositionState = rememberCameraPositionState()
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val showSheet = remember { mutableStateOf(false) }
    val showPermissionDialog = remember { mutableStateOf(false) } //fab 버튼 추가 시 필요
    val nearbyFacilitiesState = remember { mutableStateOf<List<FacilityData>>(emptyList()) }
    val navController = navController

    @SuppressLint("MissingPermission")
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                getCurrentLocation(
                    fusedLocationClient = fusedLocationClient,
                    userLatLng = userLatLng,
                    cameraPositionState = cameraPositionState
                )
            } else {
                Log.d("위치", "위치 권한이 거부되었습니다.")
            }
        }
    )

    // 최초 진입 시 권한 요청
    LaunchedEffect(Unit) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            userLatLng.value = LatLng(location.latitude, location.longitude)
        }
    }

    LaunchedEffect(Unit) {
        val facilities: MutableList<FacilityData> = mutableListOf()
        facilities += FetchMindMedApiData()
        facilities += FetchSocialDevApiData()

        Log.d("MapScreen", "API로 받은 전체 시설 개수: ${facilities.size}")
        Log.d(
            "MapScreen",
            "내위치 : ${userLatLng.value.latitude}, ${userLatLng.value.longitude}, "
        )
        facilities.sortBy { calcDistanceInMeters(userLatLng.value, it.location) }
        facilities.forEach { facility ->
            val dist = calcDistanceInMeters(userLatLng.value, facility.location)
            Log.d(
                "MapScreen",
                "시설 이름=\"${facility.name}\", 위도=${facility.location.latitude}, 경도=${facility.location.longitude}, 거리=${"%.1f".format(dist)}m"
            )
        }
        val nearby = facilities
        nearbyFacilitiesState.value = nearby
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 23.dp)
        ) {
            MapTopAppBar(title = "병원 찾기") {
                onBack()
            }

            Spacer(modifier = Modifier.size(13.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 35.dp)
            ) {
                Text(
                    text = "정신건강의학과와 상담센터, 뭐가 다를까요?",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,

                    )
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    modifier = Modifier
                        .width(42.dp)
                        .height(20.dp)
                        .background(
                            color = Color(0xFF2C2C2C),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .align(Alignment.CenterVertically)
                        .clickable(
                            onClick = { showSheet.value = true }
                        )
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Click",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.size(12.dp))

            Box(
                modifier = Modifier
                    .padding(start = 34.dp)
                    .width(301.dp)
                    .height(359.dp)
            ) {
                NaverMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    nearbyFacilitiesState.value.forEach { facility ->
                        val markerIcon =
                            if (facility.facilityType == FacilityType.COUNSELING_CENTER) {
                                OverlayImage.fromResource(R.drawable.ic_marker_center)
                            } else {
                                OverlayImage.fromResource(R.drawable.ic_marker_hospital)
                            }
                        val rawWebsite = facility.website ?: ""
                        val encodedWebsite = URLEncoder.encode(rawWebsite, "UTF-8")
                        Marker (
                            state = rememberMarkerState(position = facility.location),
                            captionText = facility.name,
                            onClick = {
                                // 마커 클릭 시 상세 화면으로 이동
                                val encodedName = URLEncoder.encode(facility.name, "UTF-8")
                                val encodedAddress = URLEncoder.encode(facility.address, "UTF-8")
                                val encodedPhone = URLEncoder.encode(facility.phone, "UTF-8")
                                val encodedLocation = URLEncoder.encode("${facility.location.latitude},${facility.location.longitude}", "UTF-8")
                                val encodedWebsite = URLEncoder.encode(facility.website ?: "", "UTF-8")
                                navController.navigate("map_detail_screen/${facility.facilityType}/${encodedName}/${encodedAddress}/${encodedPhone}/${encodedLocation}/${encodedWebsite}")
                                true
                            },
                            icon = markerIcon
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(6.dp))
            // 병원 리스트 표시
            if (nearbyFacilitiesState.value.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    nearbyFacilitiesState.value.forEach { facility ->
                        val rawWebsite = facility.website ?: ""
                        val encodedWebsite = URLEncoder.encode(rawWebsite, "UTF-8")
                        MapItem(facilityType = facility.facilityType, name = facility.name) {
                            val encodedName = URLEncoder.encode(facility.name, "UTF-8")
                            val encodedAddress = URLEncoder.encode(facility.address, "UTF-8")
                            val encodedPhone = URLEncoder.encode(facility.phone, "UTF-8")
                            val encodedLocation = URLEncoder.encode("${facility.location.latitude},${facility.location.longitude}", "UTF-8")
                            val encodedWebsite = URLEncoder.encode(facility.website ?: "", "UTF-8")
                            navController.navigate("map_detail_screen/${facility.facilityType}/${encodedName}/${encodedAddress}/${encodedPhone}/${encodedLocation}/${encodedWebsite}")
                        }
                    }
                }
            } else {
                Text(
                    text = "주변에 병원이 없습니다.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (showSheet.value) {
            ModalBottomSheet(
                onDismissRequest = { showSheet.value = false },
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    // 심리상담센터
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFCFF7D3)),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_marker_center),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("심리상담센터", fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color.Black)
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "심리상담 전문가와 50분 이상의 \n" +
                                        "상담을 통해 근본적인 원인을 찾고 \n" +
                                        "인지적/정서적 행동 변화를 할 수 있게끔 상담서비스를 제공합니다. \n" +
                                        "상담센터는 의료 기관이 아니기때문에 \n" +
                                        "의료 보험 적용되지 않습니다.\n" +
                                        " 그에 따라 현재 상태에 대한 진단과 \n" +
                                        "약물처방 또한 불가합니다.",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }

                    // 정신건강의학과
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFCFF7D3)),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_marker_hospital),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("정신건강의학과", fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color.Black)
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "정신건강의학과는 의료 보험이 \n" +
                                        "적용 가능한 의료 기관입니다.\n" +
                                        "심리상담센터에 비해  \n" +
                                        "짧은 시간(10-15분정도)동안 \n" +
                                        "의사와의 진료를  통해\n" +
                                        " 진단이 이루어집니다.\n" +
                                        "진료과정에서 진료 기록이 남게되며\n" +
                                        "필요 시 약 처방이 이뤄집니다.",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }

        // 플로팅 액션 버튼 (오른쪽 하단 고정)
        FloatingActionButton(
            onClick = {
                val permissionGranted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

                if (permissionGranted) {
                    getCurrentLocation(
                        fusedLocationClient = fusedLocationClient,
                        userLatLng = userLatLng,
                        cameraPositionState = cameraPositionState
                    )
                } else {
                    val activity = context as? android.app.Activity
                    val shouldShowRationale = activity?.let {
                        androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    } ?: false

                    if (shouldShowRationale) {
                        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        // Compose용 다이얼로그 띄우기
                        showPermissionDialog.value = true
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(100.dp)
                .padding(16.dp),
            containerColor = Color(0xFF4CAF50),
        ) {
            Image(painter = painterResource(id = R.drawable.ic_my_location), contentDescription = "현재 위치로 이동", modifier = Modifier.size(40.dp),)
        }
    }

    if (showPermissionDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showPermissionDialog.value = false
            },
            title = {
                Text("위치 권한이 필요합니다")
            },
            text = {
                Text("현재 위치를 사용하려면 앱 설정에서 위치 권한을 허용해주세요.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDialog.value = false
                    val intent = android.content.Intent(
                        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        android.net.Uri.fromParts("package", context.packageName, null)
                    )
                    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }) {
                    Text("설정으로 이동")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showPermissionDialog.value = false
                }) {
                    Text("취소")
                }
            }
        )
    }


}


@OptIn(ExperimentalNaverMapApi::class)
@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    userLatLng: MutableState<LatLng>,
    cameraPositionState: CameraPositionState
) {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val newLatLng = LatLng(location.latitude, location.longitude)
                userLatLng.value = newLatLng

                cameraPositionState.move(
                    CameraUpdate.toCameraPosition(
                        CameraPosition(newLatLng, 15.0)
                    )
                )
            }
        }
        .addOnFailureListener {
            Log.e("위치", "위치 가져오기 실패", it)
        }
}


fun calcDistanceInMeters(a: LatLng, b: LatLng): Float {
    val locA = Location("").apply {
        latitude = a.latitude
        longitude = a.longitude
    }
    val locB = Location("").apply {
        latitude = b.latitude
        longitude = b.longitude
    }
    return locA.distanceTo(locB)  // 미터 단위 반환
}

@Preview
@Composable
private fun MapScreenPreview() {
}