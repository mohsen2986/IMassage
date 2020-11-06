package com.imassage.ui.fragment.mainPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.DataRepository

class MainPageViewModelFactory(
        private val dataRepository: DataRepository
): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  MainPageViewModel(dataRepository) as T
    }
}