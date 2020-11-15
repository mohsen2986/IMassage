package com.imassage.ui.fragment.account

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.DataRepository

class AccountViewModel(
        private val dataRepository: DataRepository
) : ViewModel() {
    suspend fun updateAccount(name: String? , family: String?) =
            dataRepository.updateAccount(name , family)
}