package com.example.mindmap.map.retrofit

data class GyeonggiResponse(
    val LIST_TOTAL_COUNT : String,
    val CODE: String,
    val MESSAGE: String,
    val API_VERSION: String,
    val items: List<GyeonggiData>
)

data class GyeonggiData(
    val HOSPTL_DIV_NM: String?= null, // 병원 구분명
    val INST_NM: String, // 기관명
    val REFINE_ROADNM_ADDR : String, // 도로명 주소
    val REFINE_WGS84_LAT : String, // 위도
    val REFINE_WGS84_LOGT : String, // 경도
    val TELNO_INFO : String, // 전화번호
    val HMPG_URL : String, // 홈페이지 URL
)