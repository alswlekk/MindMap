package com.example.mindmap.map.retrofit

import android.util.Log
import com.example.mindmap.map.data.FacilityData
import com.example.mindmap.map.data.FacilityType
import com.naver.maps.geometry.LatLng
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun FetchSocialDevApiData(
): List<FacilityData> {
    val BASE_URL = "https://openapi.gg.go.kr/"
    val maxPages = 10  // 최대 10페이지까지 요청

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : ApiService = retrofit.create(ApiService::class.java)
    val allRows = mutableListOf<FacilityData>()

    try {
        for (pIndex in 1..maxPages) {
            val response = service.getSocialDevelopmentData()
            if (response.isSuccessful && response.body() != null) {
                Log.d("FetchSocialDevApiData", "응답 JSON: ${response.body()}")
                // 응답에서 Ggsoctydevelprehabt -> row를 추출, null 체크 추가
                val rows = response.body()?.Ggsoctydevelprehabt?.flatMap {
                    it.row?.let { it } ?: emptyList() // row가 null인 경우 빈 리스트로 처리
                } ?: emptyList()

                Log.d("FetchMindMedApiData", "아이템 개수 (페이지 $pIndex): ${rows.size}")

                val filterRows = rows.filter { row ->
                    row.INST_NM.contains(
                        "상담",
                        ignoreCase = true
                    ) && row.INST_NM.contains("센터", ignoreCase = true)
                }
                allRows.addAll(filterRows.map { item ->
                    FacilityData(
                        facilityType = FacilityType.COUNSELING_CENTER,
                        name = item.INST_NM,
                        address = item.REFINE_ROADNM_ADDR,
                        phone = item.TELNO,
                        location = LatLng(
                            item.REFINE_WGS84_LAT.toDouble(),
                            item.REFINE_WGS84_LOGT.toDouble()
                        ),
                        website = item.HMPG_URL
                    )
                })
            } else {
                Log.e("FetchSocialDevApiData", "API 호출 실패: ${response.code()}")
            }
        }
        Log.d("FetchSocialDevApiData", "총 아이템 개수: ${allRows.size}")
        return allRows
    } catch (e : IOException) {
        Log.e("[FetchSocialDevApiData]", "네트워크 오류: ${e.message}")
        return emptyList()
    } catch (e: Exception) {
        Log.e("[FetchSocialDevApiData]", "예상치 못한 오류: ${e.message}")
        return emptyList()
    }
}

