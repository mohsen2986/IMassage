package com.imassage.ui.dialog.account.birthday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.AccountRepository

class EditAccountBirthdayDialogViewModelFactory(
        private val accountRepository: AccountRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditAccountBirthdayDialogViewModel(accountRepository) as T
    }
}
