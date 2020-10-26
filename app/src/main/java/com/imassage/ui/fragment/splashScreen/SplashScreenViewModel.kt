package com.imassage.ui.fragment.splashScreen

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.TokenRepository

class SplashScreenViewModel(
        private val tokenRepository: TokenRepository
    ) : ViewModel() {

    val isLogin by lazy {
        tokenRepository.userIsLogin()
    }

}