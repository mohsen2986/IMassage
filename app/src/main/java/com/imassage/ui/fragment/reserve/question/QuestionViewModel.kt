package com.imassage.ui.fragment.reserve.question

import androidx.lifecycle.ViewModel
import com.imassage.data.remote.model.AnswerRequest
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository

class QuestionViewModel(
        private val dataRepository: DataRepository ,
        private val orderRepository: OrderRepository
    ): ViewModel() {
    // GET QUESTIONS
    suspend fun questions() =
            dataRepository.questions()
    // ANSWER QUESTIONS
    suspend fun answer(answers: AnswerRequest) =
            dataRepository.answer(answers)
}