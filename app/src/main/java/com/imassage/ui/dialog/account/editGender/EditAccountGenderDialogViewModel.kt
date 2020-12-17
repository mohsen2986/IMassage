package com.imassage.ui.dialog.account.editGender

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.AccountRepository

class EditAccountGenderDialogViewModel (
        private val accountRepository: AccountRepository
): ViewModel(){

    suspend fun updateAccount(gender: String?) =
            accountRepository.updateAccount(name = null , family = null , gender = gender , address = null , birthday = null)

}