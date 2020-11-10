package com.imassage.ui.fragment.reserve.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuestionViewModelFactory(
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelclass: Class<T>): T{
        return QuestionViewModel() as T
    }
}