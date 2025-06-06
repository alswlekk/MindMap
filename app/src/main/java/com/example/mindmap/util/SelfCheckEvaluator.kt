package com.example.mindmap.util

class SelfCheckEvaluator {
    fun evaluate(score: Int): Pair<String, String> {
        return when (score) {
            in 0..4 -> "정상 범위" to "심리적으로 안정된 상태입니다."
            in 5..9 -> "가벼운 우울" to "스트레스를 해소하고 충분한 휴식을 취하세요."
            in 10..14 -> "중간 정도의 우울" to "일상에 영향을 줄 수 있으므로 전문가의 상담을 권장합니다."
            else -> "심한 우울" to "전문적인 도움이 필요합니다."
        }
    }
}
