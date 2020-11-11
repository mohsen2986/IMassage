package com.imassage.ui.fragment.reserveView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReserveViewViewModelFactory(
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelclass: Class<T>): T{
        return ReserveViewViewModel()  as T
    }
}