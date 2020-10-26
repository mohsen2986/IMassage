package com.imassage.ui.fragment.phoneVerification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.UserInformationRepository

class PhoneVerificationViewModelFactory(
        private val userInformationRepository: UserInformationRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhoneVerificationViewModel(userInformationRepository) as T
    }
}