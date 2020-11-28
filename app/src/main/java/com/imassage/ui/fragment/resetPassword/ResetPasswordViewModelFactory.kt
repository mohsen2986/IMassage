package com.imassage.ui.fragment.resetPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.UserInformationRepository

class ResetPasswordViewModelFactory(
    private val userInformationRepository: UserInformationRepository
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ResetPasswordViewModel(userInformationRepository) as T
    }
}