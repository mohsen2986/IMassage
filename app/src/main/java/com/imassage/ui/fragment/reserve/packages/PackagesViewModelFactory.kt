package com.imassage.ui.fragment.reserve.packages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository

class PackagesViewModelFactory(
        private val dataRepository: DataRepository ,
        private val orderRepository: OrderRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PackagesViewModel(dataRepository , orderRepository) as T
    }
}