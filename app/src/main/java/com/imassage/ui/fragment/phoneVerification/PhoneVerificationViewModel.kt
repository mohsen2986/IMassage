package com.imassage.ui.fragment.phoneVerification

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.UserInformationRepository

class PhoneVerificationViewModel(
        private val userInformationRepository: UserInformationRepository
) : ViewModel() {
    suspend fun sendVerificationCode(code: String , type: String) =
            if(type == "register" ) userInformationRepository.registerVerifySms(code)
            else userInformationRepository.loginVerifySms(code)
}