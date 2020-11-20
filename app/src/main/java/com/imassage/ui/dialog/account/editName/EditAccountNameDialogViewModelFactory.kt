package com.imassage.ui.dialog.account.editName

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.AccountRepository
import com.imassage.data.repository.OrderRepository

class EditAccountNameDialogViewModelFactory(
        private val accountRepository: AccountRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditAccountNameViewModel(accountRepository) as T
    }
}
