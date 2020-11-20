package com.imassage.ui.dialog.account.editName

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.AccountRepository

class EditAccountNameViewModel (
        private val accountRepository: AccountRepository
): ViewModel(){

    suspend fun updateAccount(name: String?) =
            accountRepository.updateAccount(name , family = null , gender = null)
}