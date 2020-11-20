package com.imassage.ui.fragment.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.TokenRepository

class SplashScreenViewModelFactory(
        private val tokenRepository: TokenRepository ,
        private val dataRepository: DataRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashScreenViewModel(tokenRepository , dataRepository) as T
    }
}