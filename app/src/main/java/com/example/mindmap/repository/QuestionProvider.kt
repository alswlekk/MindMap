package com.example.mindmap.repository

import com.example.mindmap.model.Question

object QuestionProvider {
    private val questions = listOf(
        Question(1, listOf("무기력하거나 기운이 없게 느껴졌나요?", "모든 게 귀찮고 의욕이 없었나요?", "하루종일 기운이 없었다고 느꼈나요?")),
        Question(2, listOf("일상에서 즐거움을 느끼지 못했나요?", "기존에 좋아하던 것들이 시큰둥했나요?", "재미있는 일이 별로 없었나요?")),
        Question(3, listOf("잠에 문제가 있었나요?", "자주 깨거나 너무 오래 자진 않았나요?", "불면 혹은 과도한 수면이 있었나요?")),
        Question(4, listOf("식욕이 줄거나 반대로 많이 먹진 않았나요?", "입맛이 없었거나 폭식했던 적이 있었나요?", "식사량이 평소보다 달랐던 적이 있었나요?")),
        Question(5, listOf("움직임이 느려지거나 너무 불안하진 않았나요?", "몸이 무겁거나 초조한 느낌이 있었나요?", "가만히 있기 힘든 느낌이 있었나요?")),
        Question(6, listOf("피곤하거나 기운이 빠졌다고 느꼈나요?", "별일 안 해도 지쳤던 적이 있었나요?", "에너지가 없고 무기력했던 적이 있었나요?")),
        Question(7, listOf("자신을 실패자처럼 느낀 적이 있었나요?", "자신을 탓한 적이 있었나요?", "내가 실망스럽다고 느낀 적이 있었나요?")),
        Question(8, listOf("집중이 어려웠던 적이 있었나요?", "TV, 책에 집중이 안 되었나요?", "산만하거나 집중력이 떨어졌나요?")),
        Question(9, listOf("죽고 싶거나 자해 생각을 한 적이 있었나요?", "사라지고 싶다는 생각이 들었나요?", "나를 해치고 싶은 마음이 든 적이 있었나요?"))
    )

    fun getShuffledRandomQuestions(): List<Pair<Int, String>> {
        return questions.shuffled().map { it.id to it.expressions.random() }
    }
}
