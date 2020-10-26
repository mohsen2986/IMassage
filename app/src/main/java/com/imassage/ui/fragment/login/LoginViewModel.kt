package com.imassage.ui.fragment.login

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.UserInformationRepository

class LoginViewModel(
        private val userInformationRepository: UserInformationRepository
) : ViewModel() {
    suspend fun login(phone: String , password: String) =
            userInformationRepository.login(phone , password)
}