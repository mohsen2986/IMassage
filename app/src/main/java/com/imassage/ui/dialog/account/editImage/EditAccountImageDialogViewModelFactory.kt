package com.imassage.ui.dialog.account.editImage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.AccountRepository
import com.imassage.ui.dialog.account.editName.EditAccountNameViewModel

class EditAccountImageDialogViewModelFactory(
      private val accountRepository: AccountRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditAccountImageViewModel(accountRepository) as T
    }
}
