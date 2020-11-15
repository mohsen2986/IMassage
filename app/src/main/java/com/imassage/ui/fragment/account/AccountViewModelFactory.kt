package com.imassage.ui.fragment.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.DataRepository

class AccountViewModelFactory(
        private val dataRepository: DataRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelclass: Class<T>): T{
        return AccountViewModel(dataRepository) as T
    }
}