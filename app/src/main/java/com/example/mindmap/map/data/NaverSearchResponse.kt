package com.example.mindmap.map.data


import com.naver.maps.geometry.LatLng
import getOpenHours

data class NaverSearchResponse(
    val items: List<NaverPlaceItem>?
)

data class NaverPlaceItem(
    val title : String,
    val link : String,
    val websiteLink: String? = null,
    val category : String,
    val description : String,
    val telephone : String?,
    val address : String,
    val roadAddress : String,
    val mapx : String, // 경도 (string -> double)
    val mapy : String,  // 위도 (string -> double)
)

suspend fun NaverPlaceItem.toFacilityData() : FacilityData {
    val cleanedTitle = title.replace(Regex("<.*?>"), "")
    val operatingHours = if (link.isNotBlank()) getOpenHours(link) else null  // link가 빈 값이면 null 반환

    return FacilityData(
        facilityType = when {
            category.contains("상담", ignoreCase = true) -> FacilityType.COUNSELING_CENTER
            else -> FacilityType.PSYCHIATRIC_HOSPITAL
        },
        name = cleanedTitle,
        address = roadAddress.ifBlank { address },
        phone = telephone ?: "",
        location = LatLng(mapy.toDouble(), mapx.toDouble()),
        // 운영시간은 크롤링으로 받아올 예정
        operatingHours = operatingHours,
        website = link,
    )
}
