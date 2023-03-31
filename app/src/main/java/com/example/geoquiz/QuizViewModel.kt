package com.example.geoquiz

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    var currentIndex = 0
    var isCheater = false

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private val userAnswers = arrayOfNulls<Boolean>(questionBank.size)
    val cheatedAnswers = arrayOfNulls<Boolean>(questionBank.size)

    fun setCurrentAnswer(answer : Boolean) :Boolean{
        val userResult = (answer == currentQuestionAnswer)
        userAnswers[currentIndex] = userResult
        return userResult
    }

    val correctPercents: Int?
        get() {
            var correctCount = 0.0
            for (answer in userAnswers) {
                if (answer == null) return null
                if (answer) correctCount++
            }
            return (correctCount / questionBank.size * 100).toInt()
        }

    val cheatCount: Int?
        get() {
            var cheat = 0
            for (answer in cheatedAnswers) {
                if (answer == null) continue
                if (answer) cheat++
            }
            return cheat
        }

    val isCurrentAnswered: Boolean
        get() = userAnswers[currentIndex] != null

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveTo(index: Int){
        if(index < 0) return
        if(index >= questionBank.size) return
        currentIndex = index
    }

    fun moveToNext() {
        moveTo((currentIndex + 1) % questionBank.size)
    }

    fun moveToPrev() {
        moveTo((currentIndex - 1 + questionBank.size) % questionBank.size)
    }
}
