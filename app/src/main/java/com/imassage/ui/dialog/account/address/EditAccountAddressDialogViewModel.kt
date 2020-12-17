package com.imassage.ui.dialog.account.address

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.AccountRepository

class EditAccountAddressDialogViewModel (
        private val accountRepository: AccountRepository
): ViewModel(){
    suspend fun updateAccount(address: String?) =
            accountRepository.updateAccount(name = null , family = null , gender = null , address = address , birthday = null)

}