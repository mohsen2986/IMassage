package com.imassage.ui.fragment.mainPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.AccountRepository
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.OrderRepository
import com.imassage.data.repository.TokenRepository

class MainPageViewModelFactory(
        private val dataRepository: DataRepository ,
        private val tokenRepository: TokenRepository ,
        private val orderRepository: OrderRepository ,
        private val accountRepository: AccountRepository
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  MainPageViewModel(dataRepository , tokenRepository , orderRepository ,accountRepository) as T
    }
}