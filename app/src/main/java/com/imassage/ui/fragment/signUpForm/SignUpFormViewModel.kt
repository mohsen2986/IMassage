package com.imassage.ui.fragment.signUpForm

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.AccountRepository

class SignUpFormViewModel(
        private val accountRepository: AccountRepository
) : ViewModel() {

    suspend fun sendAddressAndBirthday(birthDay: String? , address: String?) =
            accountRepository.updateAccount(null , null , null , address , birthDay)
    // consulting
    suspend fun consulting() =
            accountRepository.consulting()

}