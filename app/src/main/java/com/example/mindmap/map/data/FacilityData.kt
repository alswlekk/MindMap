package com.example.mindmap.map.data

import com.naver.maps.geometry.LatLng

data class FacilityData(
    val facilityType: FacilityType,
    val name : String,
    val address : String,
    val phone : String,
    val location : LatLng,
    val website: String? = null
)
