package com.example.mindmap.map.retrofit

import android.content.Context
import android.util.Log
import com.example.mindmap.map.data.FacilityData
import com.example.mindmap.map.data.FacilityType
import com.google.gson.Gson
import com.naver.maps.geometry.LatLng
import java.io.InputStreamReader

// Ggmindmedinst 데이터에서 JSON 파일을 읽어오는 함수
fun loadGgmindmedinstJson(context: Context, fileName: String): String {
    Log.d("loadGgmindmedinstJson", "파일 이름: $fileName")
    val assetManager = context.assets
    Log.d("loadGgmindmedinstJson", "파일 경로: ${assetManager.javaClass.getResource(fileName)}")
    val inputStream = assetManager.open(fileName)
    if (inputStream == null) {
        Log.e("loadGgmindmedinstJson", "파일을 찾을 수 없습니다: $fileName")
        return ""
    }
    val inputStreamReader = InputStreamReader(inputStream)
    val bufferedReader = inputStreamReader.buffered()
    val jsonString = bufferedReader.use { it.readText() }
    Log.d("loadGgmindmedinstJson", "JSON 파일 로드 완료: $fileName")
    Log.d("loadGgmindmedinstJson", "JSON 내용: $jsonString")
    return jsonString
}

// JSON 문자열을 FacilityData 리스트로 변환하는 함수
fun parseGgmindmedinstJsonToFacilityData(jsonString: String): List<FacilityData> {
    val gson = Gson()
    val jsonObject = gson.fromJson(jsonString, JsonResponse::class.java)

    // 'row' 리스트가 FacilityData로 변환
    val rows = jsonObject.Ggmindmedinst[1].row
    return rows?.map { item ->
        FacilityData(
            facilityType = FacilityType.PSYCHIATRIC_HOSPITAL,
            name = item.INST_NM,
            address = item.REFINE_ROADNM_ADDR,
            phone = item.TELNO_INFO,
            location = LatLng(item.REFINE_WGS84_LAT.toDouble(), item.REFINE_WGS84_LOGT.toDouble()),
            website = item.HMPG_URL
        )
    } ?: emptyList() // rows가 null인 경우 빈 리스트 반환
}

// JSON 응답을 래핑하는 데이터 클래스
data class JsonResponse(
    val Ggmindmedinst: List<GgmindmedinstData>
)

data class GgmindmedinstData(
    val row: List<RowData>?
)

data class RowData(
    val INST_NM: String,
    val TELNO_INFO: String,
    val HMPG_URL: String?,
    val REFINE_ROADNM_ADDR: String,
    val REFINE_WGS84_LAT: String,
    val REFINE_WGS84_LOGT: String
)

// 로컬 JSON 파일에서 Ggmindmedinst 데이터 불러오는 함수
fun FetchMindMedDataFromJson(context: Context, fileName: String): List<FacilityData> {
    try {
        val jsonString = loadGgmindmedinstJson(context, fileName)
        return parseGgmindmedinstJsonToFacilityData(jsonString)
    } catch (e: Exception) {
        Log.e("FetchMindMedData", "오류 발생: ${e.message}")
        Log.e("FetchMindMedData", "파일 이름: $fileName")
        Log.e("FetchMindMedData", "파일 경로: ${context.assets.javaClass.getResource(fileName)}")
        return emptyList()
    }
}

// JSON 응답을 래핑하는 데이터 클래스
data class JsonResponseGgsoctydevelprehabt(
    val Ggsoctydevelprehabt: List<GgsoctydevelprehabtData>
)

data class GgsoctydevelprehabtData(
    val row: List<RowDataGgsoctydevelprehabt>?
)

data class RowDataGgsoctydevelprehabt(
    val INST_NM: String,
    val TELNO: String,
    val HMPG_URL: String?,
    val REFINE_ROADNM_ADDR: String,
    val REFINE_WGS84_LAT: String,
    val REFINE_WGS84_LOGT: String
)

// 로컬 JSON 파일에서 Ggsoctydevelprehabt 데이터 불러오는 함수
fun FetchSocialDevDataFromJson(context: Context, fileName: String): List<FacilityData> {
    try {
        val jsonString = loadGgsoctydevelprehabtJson(context, fileName)
        return parseGgsoctydevelprehabtJsonToFacilityData(jsonString)
    } catch (e: Exception) {
        Log.e("FetchSocialDevData", "오류 발생: ${e.message}")
        return emptyList()
    }
}

// JSON 문자열을 FacilityData 리스트로 변환하는 함수
fun parseGgsoctydevelprehabtJsonToFacilityData(jsonString: String): List<FacilityData> {
    val gson = Gson()

    val jsonObject = gson.fromJson(jsonString, JsonResponseGgsoctydevelprehabt::class.java)

    // 'row' 리스트가 FacilityData로 변환
    val rows = jsonObject.Ggsoctydevelprehabt[1].row
    Log.d("parseGgsoctydevelprehabtJsonToFacilityData", "아이템 개수: ${rows?.size ?: 0}")
    val filteredRows = rows?.filter { row ->
        row.INST_NM.contains("상담", ignoreCase = true) && row.INST_NM.contains(
            "센터",
            ignoreCase = true
        )
    } ?: emptyList()
    return filteredRows.map { item ->
        FacilityData(
            facilityType = FacilityType.COUNSELING_CENTER,
            name = item.INST_NM,
            address = item.REFINE_ROADNM_ADDR,
            phone = item.TELNO,
            location = LatLng(item.REFINE_WGS84_LAT.toDouble(), item.REFINE_WGS84_LOGT.toDouble()),
            website = item.HMPG_URL
        )
    } // null인 경우 빈 리스트 반환
}

// Ggsoctydevelprehabt 데이터에서 JSON 파일을 읽어오는 함수
fun loadGgsoctydevelprehabtJson(context: Context, fileName: String): String {
    val assetManager = context.assets
    val inputStream = assetManager.open(fileName)
    val inputStreamReader = InputStreamReader(inputStream)
    val bufferedReader = inputStreamReader.buffered()
    val jsonString = bufferedReader.use { it.readText() }
    Log.d("loadGgsoctydevelprehabtJson", "JSON 파일 로드 완료: $fileName")
    Log.d("loadGgsoctydevelprehabtJson", "JSON 내용: $jsonString")
    return jsonString
}


