package com.imassage.ui.fragment.receipt

import androidx.lifecycle.ViewModel
import com.imassage.data.model.Package
import com.imassage.data.repository.AccountRepository
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository

class ReceiptViewModel(
        private val orderRepository: OrderRepository ,
        private val accountRepository: AccountRepository ,
        private  val dataRepository: DataRepository
) : ViewModel() {
    suspend fun transaction() =
            orderRepository.transaction(null)

    // set the offer code
    suspend fun makeOffer(transactionId: String , offerCode: String) =
            orderRepository.offer(transactionId , offerCode)

    // set transaction id
    fun setTransaction(transactionId: String){
        orderRepository.transactionId = transactionId
    }
    fun account() =
            accountRepository.account

    fun getGender() =
            orderRepository.gender
    fun getTime() =
            orderRepository.time
    fun getDate() =
            orderRepository.date

    fun packages() =
            orderRepository.packages
    fun massage() =
            orderRepository.massage
}