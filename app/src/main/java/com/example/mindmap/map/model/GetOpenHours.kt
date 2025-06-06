package com.example.mindmap.map.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

// 크롤링을 비동기적으로 처리
suspend fun getOpenHours(url: String): String? {
    return withContext(Dispatchers.IO) {  // IO 작업은 백그라운드 스레드에서 실행
        try {
            val doc = Jsoup.connect(url).get()  // URL에서 HTML 문서 가져오기
            val operatingHoursElement = doc.selectFirst("div.H3ua4")  // 운영 시간 정보가 담긴 div 요소 찾기
            operatingHoursElement?.text()  // 텍스트만 반환
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}