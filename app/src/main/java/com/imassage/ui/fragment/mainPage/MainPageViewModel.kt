package com.imassage.ui.fragment.mainPage

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.AccountRepository
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository
import com.imassage.data.repository.TokenRepository
import com.imassage.ui.base.lazyDeferred

class MainPageViewModel(
        private val dataRepository: DataRepository ,
        private val tokenRepository: TokenRepository ,
        private val orderRepository: OrderRepository ,
        private val accountRepository: AccountRepository
) : ViewModel() {
//    val mainPage by lazyDeferred {
//        dataRepository.mainPage()
//    }
    // log out from the system
    suspend fun logOut() =
        tokenRepository.userLogOut()

    suspend fun mainPage() =
        dataRepository.mainPage()

    fun mainPageData() =
            dataRepository.mainPage

    fun packageData() =
            dataRepository.packages

    // set gender for reserve time
    fun setMaleGender() {
        orderRepository.gender = true
    }
    fun setFemaleGender(){
        orderRepository.gender = false
    }
    fun getAccountData() =
            accountRepository.account
}