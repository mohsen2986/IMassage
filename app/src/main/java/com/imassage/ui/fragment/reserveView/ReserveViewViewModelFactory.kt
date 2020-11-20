package com.imassage.ui.fragment.reserveView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.OrderRepository

class ReserveViewViewModelFactory(
        private val orderRepository: OrderRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelclass: Class<T>): T{
        return ReserveViewViewModel(orderRepository)  as T
    }
}