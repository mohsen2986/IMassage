package com.imassage.ui.fragment.reserve.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imassage.data.repository.DataRepository

class QuestionViewModelFactory(
        private var dataRepository: DataRepository
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelclass: Class<T>): T{
        return QuestionViewModel(dataRepository) as T
    }
}