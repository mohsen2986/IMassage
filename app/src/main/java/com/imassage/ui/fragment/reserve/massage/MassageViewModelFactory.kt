package com.imassage.ui.fragment.reserve.massage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MassageViewModelFactory(
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MassageViewModel() as T
    }
}