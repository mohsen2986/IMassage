package com.imassage.ui.fragment.reserve.massage

import androidx.lifecycle.ViewModel
import com.imassage.data.model.Massage
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository

class MassageViewModel(
    private val dataRepository: DataRepository ,
    private val orderRepository: OrderRepository
    ) : ViewModel() {

    fun mainPageData() =
        dataRepository.mainPage

    fun massageId(massageId: String) {
        orderRepository.massageId = massageId
    }
    fun massage(massage: Massage) {
        orderRepository.massage = massage
    }
    suspend fun massages() =
            dataRepository.massages()
            
}