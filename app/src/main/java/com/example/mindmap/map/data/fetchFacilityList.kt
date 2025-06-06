package com.example.mindmap.map.data

import android.util.Log
import android.util.Log.e
import com.example.mindmap.map.model.NaverSearchService
import com.naver.maps.geometry.LatLng
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun fetchFacilityList(
    query: String,
    clientId: String,
    clientSecret: String,
    userLocation : LatLng
): List<FacilityData> {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(NaverSearchService::class.java)

    return try {

        Log.d("NAVER_REQ", "X-Naver-Client-Id=$clientId, X-Naver-Client-Secret=$clientSecret")
        Log.d("NAVER_REQ", "Query=$query")

        val response = service.searchFacilities(
            clientId = clientId,
            clientSecret = clientSecret,
            query = query,
            longitude = userLocation.longitude,
            latitude = userLocation.latitude
        )
        if (response.isSuccessful && response.body() != null) {
            // body()에서 실제 NaverSearchResponse 객체를 꺼내서,
            Log.d("NAVER_RESP", "응답 JSON: ${response.body()}")
            val list = response.body()!!.items
            Log.d("fetchFacilityList", list.toString())
            list?.forEach { item ->
                Log.d("NAVER_JSON", "title=${item.title}, telephone=${item.telephone}")
            }
            val body: NaverSearchResponse = response.body()!!
            Log.d("fetchFacilityList", "items 개수=${body.items?.size}")
            body.items?.map { it.toFacilityData() } ?:emptyList()
        } else {
            // 예: HTTP 401, 403, 500 등의 경우
            Log.e(
                "[fetch]API 에러",
                "HTTP ${response.code()} - ${response.errorBody()?.string().orEmpty()}"
            )
            emptyList()
    }
    }
    catch (e : IOException) {
        Log.e("[fetch]네트워크 오류", "네트워크 오류: ${e.message}")
        emptyList()
    }
    catch (e: Exception) {
        Log.e("[fetch]API 호출 실패", e.message ?: "알 수 없는 오류")
        emptyList()
    }
}
