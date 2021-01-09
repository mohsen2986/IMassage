package com.imassage.ui.dialog.account.birthday

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.AccountRepository

class EditAccountBirthdayDialogViewModel (
        private val accountRepository: AccountRepository
): ViewModel(){
    suspend fun updateBirthday(birthday: String?) =
            accountRepository.updateAccount(name = null , family = null , gender = null , address = null , birthday = birthday)

}