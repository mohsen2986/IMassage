package com.imassage.ui.fragment.mainPage

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.DataRepository
import com.imassage.data.repository.TokenRepository
import com.imassage.ui.base.lazyDeferred

class MainPageViewModel(
        private val dataRepository: DataRepository ,
        private val tokenRepository: TokenRepository
) : ViewModel() {
//    val mainPage by lazyDeferred {
//        dataRepository.mainPage()
//    }
    // log out from the system
    suspend fun logOut() =
        tokenRepository.userLogOut()

    suspend fun mainPage() =
        dataRepository.mainPage()
}