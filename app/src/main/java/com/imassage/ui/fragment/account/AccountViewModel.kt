package com.imassage.ui.fragment.account

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.AccountRepository
import com.imassage.data.repository.DataRepository

class AccountViewModel(
        private val accountRepository: AccountRepository
) : ViewModel() {
    suspend fun accountInformation() =
            accountRepository.userInformation()

//    suspend fun updateAccount(name: String? , family: String?) =
//            accountRepository.updateAccount(name , family)
}