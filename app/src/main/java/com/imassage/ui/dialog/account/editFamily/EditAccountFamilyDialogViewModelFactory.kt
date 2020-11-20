package com.imassage.ui.dialog.account.editFamily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.AccountRepository
import com.imassage.ui.dialog.account.editName.EditAccountNameViewModel

class EditAccountFamilyDialogViewModelFactory(
        private val accountRepository: AccountRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditAccountFamilyDialogViewModel(accountRepository) as T
    }
}
