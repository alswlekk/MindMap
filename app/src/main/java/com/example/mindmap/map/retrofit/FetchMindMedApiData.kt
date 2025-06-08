package com.example.mindmap.map.retrofit

import android.util.Log
import android.util.Log.e
import com.example.mindmap.map.data.FacilityData
import com.example.mindmap.map.data.FacilityType
import com.naver.maps.geometry.LatLng
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun FetchMindMedApiData(): List<FacilityData> {
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

            val response = service.getMindMedInstData()
            if (response.isSuccessful && response.body() != null) {
                Log.d("FetchMindMedApiData", "응답 JSON: ${response.body()}")
                // 응답에서 Ggmindmedinst -> row를 추출, null 체크 추가
                val rows = response.body()?.Ggmindmedinst?.flatMap {
                    it.row?.let { it } ?: emptyList() // row가 null인 경우 빈 리스트로 처리
                } ?: emptyList() // Ggmindmedinst가 null일 경우 빈 리스트 반환

                Log.d("FetchMindMedApiData", "item 개수 : ${rows.size}")
                allRows.addAll(rows.map { item ->
                    FacilityData(
                        facilityType = FacilityType.PSYCHIATRIC_HOSPITAL,
                        name = item.INST_NM,
                        address = item.REFINE_ROADNM_ADDR,
                        phone = item.TELNO_INFO,
                        location = LatLng(
                            item.REFINE_WGS84_LAT.toDouble(),
                            item.REFINE_WGS84_LOGT.toDouble()
                        ),
                        website = item.HMPG_URL
                    )
                })
            } else {
                Log.e("FetchMindMedApiData", "API 호출 실패: ${response.code()}")
            }
        }
        Log.d("FetchMindMedApiData", "총 아이템 개수: ${allRows.size}")
        return allRows
    } catch (e : IOException) {
        Log.e("[fetchMindMedApiData]", "네트워크 오류: ${e.message}")
        return emptyList()
    } catch (e: Exception) {
        Log.e("[fetchMindMedApiData]", "예상치 못한 오류: ${e.message}")
        return emptyList()
    }
}

