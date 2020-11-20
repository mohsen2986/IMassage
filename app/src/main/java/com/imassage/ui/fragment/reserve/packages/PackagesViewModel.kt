package com.imassage.ui.fragment.reserve.packages

import androidx.lifecycle.ViewModel
import com.imassage.data.model.Package
import com.imassage.data.remote.model.AnswerRequest
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository

class PackagesViewModel(
        private val dataRepository: DataRepository ,
        private val orderRepository: OrderRepository
) : ViewModel() {

    fun packages() =
            dataRepository.packages

    fun packageId(packageId: String) {
        orderRepository.packageId = packageId
    }
    fun Package_(package_ : Package) {
        orderRepository.packages = package_
    }
}