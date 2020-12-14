package com.imassage.ui.fragment.splashScreen

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.TokenRepository

class SplashScreenViewModel(
        private val tokenRepository: TokenRepository ,
        private val dataRepository: DataRepository
    ) : ViewModel() {

    val isLogin by lazy {
        tokenRepository.userIsLogin()
    }
    fun fetchMainData() = 
            dataRepository.mainPage

    suspend fun checkFormFilled() =
            dataRepository.checkFormFilled()

}