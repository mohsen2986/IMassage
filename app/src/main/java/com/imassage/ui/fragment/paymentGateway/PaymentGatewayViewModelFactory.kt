package com.imassage.ui.fragment.paymentGateway

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.OrderRepository

class PaymentGatewayViewModelFactory(
        private val orderRepository: OrderRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentGatewayViewModel(orderRepository) as T
    }
}