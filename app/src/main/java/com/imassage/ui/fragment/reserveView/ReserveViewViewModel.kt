package com.imassage.ui.fragment.reserveView

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.OrderRepository

class ReserveViewViewModel(
        private val orderRepository: OrderRepository
) : ViewModel() {
    suspend fun ordersHistory() =
            orderRepository.ordersHistory()

    suspend fun reserves() =
            orderRepository.orders()
}