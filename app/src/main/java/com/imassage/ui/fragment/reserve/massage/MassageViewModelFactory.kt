package com.imassage.ui.fragment.reserve.massage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository

class MassageViewModelFactory(
        private val dataRepository: DataRepository ,
        private val orderRepository: OrderRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MassageViewModel(dataRepository , orderRepository) as T
    }
}