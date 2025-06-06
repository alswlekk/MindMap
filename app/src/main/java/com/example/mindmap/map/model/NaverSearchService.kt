package com.example.mindmap.map.model

import com.example.mindmap.map.data.FacilityData
import com.example.mindmap.map.data.NaverSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverSearchService {
    @GET("v1/search/local.json")
    suspend fun searchFacilities(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int = 20,
        @Query("start") start: Int = 1,
        @Query("sort") sort: String = "random"
    ): NaverSearchResponse
}