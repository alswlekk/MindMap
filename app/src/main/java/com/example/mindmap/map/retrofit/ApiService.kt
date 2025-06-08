package com.example.mindmap.map.retrofit

import com.example.mindmap.BuildConfig
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    // 첫 번째 API 호출
    @GET("Ggmindmedinst")  // 경기도 정신의료기관 정보
    suspend fun getMindMedInstData(
        @Header("KEY") key: String = BuildConfig.API_KEY,
        @Query("Type") type: String = "json",
        @Query("pIndex") pIndex: Int = 1,
        @Query("pSize") pSize: Int = 50
    ): Response<MedResponse>

    // 두 번째 API 호출
    @GET("Ggsoctydevelprehabt") // 경기도 사회복지시설 정보
    suspend fun getSocialDevelopmentData(
        @Header("KEY") key: String = BuildConfig.API_KEY,
        @Query("Type") type: String = "json",
        @Query("pIndex") pIndex: Int = 1,
        @Query("pSize") pSize: Int = 50
    ): Response<CenterResponse>
}