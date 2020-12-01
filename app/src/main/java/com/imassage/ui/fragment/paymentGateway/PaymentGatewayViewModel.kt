package com.imassage.ui.fragment.paymentGateway

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.OrderRepository

class PaymentGatewayViewModel(
        private val orderRepository: OrderRepository
) : ViewModel() {
    suspend fun payTransaction(refId: String) =
            orderRepository.payTransaction(refId)

    suspend fun sendOrder() =
            orderRepository.sendOrder()

    suspend fun setTransaction(transactionId: String){
        orderRepository.transactionId = transactionId
    }
}