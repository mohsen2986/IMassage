package com.imassage.ui.fragment.reserve.reserveTime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.OrderRepository

class ReservedTimeViewModelFactory(
        private val orderRepository: OrderRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel?> create(modelclass: Class<T>): T{
        return ReservedTimeViewModel(orderRepository) as T
    }
}