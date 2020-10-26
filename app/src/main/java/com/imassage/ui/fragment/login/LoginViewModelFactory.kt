package com.imassage.ui.fragment.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.UserInformationRepository

class LoginViewModelFactory(
        private val userInformationRepository: UserInformationRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(userInformationRepository) as T
    }
}