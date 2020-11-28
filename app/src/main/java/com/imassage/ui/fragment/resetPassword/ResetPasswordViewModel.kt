package com.imassage.ui.fragment.resetPassword

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.UserInformationRepository

class ResetPasswordViewModel(
    private val userInformationRepository: UserInformationRepository
) : ViewModel() {

    suspend fun sendCode(code: String , password: String) =
        userInformationRepository.resetPasswordVerification(code , password)

}