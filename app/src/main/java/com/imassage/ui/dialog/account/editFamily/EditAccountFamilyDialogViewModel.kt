package com.imassage.ui.dialog.account.editFamily

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.AccountRepository

class EditAccountFamilyDialogViewModel (
        private val accountRepository: AccountRepository
): ViewModel(){
    suspend fun updateAccount(family: String?) =
            accountRepository.updateAccount(name = null , family = family , gender = null, address = null , birthday = null)

}