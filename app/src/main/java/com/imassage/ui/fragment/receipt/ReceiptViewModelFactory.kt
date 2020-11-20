package com.imassage.ui.fragment.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.AccountRepository
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository

class ReceiptViewModelFactory(
        private val orderRepository: OrderRepository ,
        private val accountRepository: AccountRepository ,
        private val dataRepository: DataRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReceiptViewModel(orderRepository , accountRepository , dataRepository) as T
    }
}