package com.imassage.ui.fragment.reserve.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository

class QuestionViewModelFactory(
        private var dataRepository: DataRepository ,
        private val orderRepository: OrderRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelclass: Class<T>): T{
        return QuestionViewModel(dataRepository , orderRepository) as T
    }
}