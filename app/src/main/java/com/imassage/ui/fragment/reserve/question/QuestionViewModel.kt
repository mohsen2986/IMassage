package com.imassage.ui.fragment.reserve.question

import androidx.lifecycle.ViewModel
import com.imassage.data.remote.model.AnswerRequest
import com.imassage.data.repository.DataRepository

class QuestionViewModel(
        private val dataRepository: DataRepository
    ): ViewModel() {
    // ANSWER QUESTIONS
    suspend fun answer(answers: AnswerRequest) =
            dataRepository.answer(answers)
}