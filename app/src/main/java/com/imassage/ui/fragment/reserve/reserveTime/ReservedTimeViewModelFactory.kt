package com.imassage.ui.fragment.reserve.reserveTime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReservedTimeViewModelFactory(
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel?> create(modelclass: Class<T>): T{
        return ReservedTimeViewModel() as T
    }
}