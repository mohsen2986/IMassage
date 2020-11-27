package com.imassage.ui.dialog.resetPassword

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.UserInformationRepository

class ResetPasswordDialogViewModel(
    private val userInformationRepository: UserInformationRepository
) : ViewModel() {
    suspend fun resetPassword(phone: String) =
        userInformationRepository.resetPassword(phone)
}