package com.imassage.ui.fragment.reserve.reserveTime

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.OrderRepository

class ReservedTimeViewModel(
        private val orderRepository: OrderRepository
) : ViewModel() {

    suspend fun availableDates() =
            orderRepository.availableDates

    suspend fun availableDateTime(date: String) =
            orderRepository.availableDateTime(date)

    fun gender() =
            orderRepository.gender
    fun setDate(date: String) {
        orderRepository.date = date
    }
    fun setTime(time: String){
        orderRepository.time = time
    }
    suspend fun checkReserveTime() =
            orderRepository.checkTime()
}