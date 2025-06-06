package com.example.mindmap.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mindmap.repository.QuestionProvider

class CheckSessionViewModel : ViewModel() {
    val shuffledQuestions = QuestionProvider.getShuffledRandomQuestions()
    val answers = mutableStateListOf<Int?>().apply { repeat(9) { add(null) } }

    private var totalScore: Int = 0

    fun allAnswered() = answers.none { it == null }
    fun getTotalScore() = answers.filterNotNull().sum()

    
    fun submitAnswers(score: Int) {
        totalScore = score
    }

    fun getSubmittedScore(): Int = totalScore

    
    fun clearSession() {
        answers.replaceAll { null }
        totalScore = 0
    }


}