package com.example.mindmap.map.data

import android.util.Log
import com.example.mindmap.map.model.NaverSearchService
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun fetchFacilityList(
    query: String,
    clientId: String,
    clientSecret: String
): List<FacilityData> {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(NaverSearchService::class.java)

    return try {
        val result = service.searchFacilities(
            clientId = clientId,
            clientSecret = clientSecret,
            query = query
        )
        result.items.map { it.toFacilityData() }
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
