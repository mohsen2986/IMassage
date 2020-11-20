package com.imassage.ui.fragment.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.AccountRepository
import com.imassage.data.repository.DataRepository

class AccountViewModelFactory(
        private val accountRepository: AccountRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelclass: Class<T>): T{
        return AccountViewModel(accountRepository) as T
    }
}