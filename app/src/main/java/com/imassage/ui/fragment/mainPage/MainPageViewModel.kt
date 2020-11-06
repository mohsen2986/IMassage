package com.imassage.ui.fragment.mainPage

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.DataRepository
import com.imassage.ui.base.lazyDeferred

class MainPageViewModel(
        private val dataRepository: DataRepository
) : ViewModel() {
//    val mainPage by lazyDeferred {
//        dataRepository.mainPage()
//    }
    suspend fun mainPage() =
        dataRepository.mainPage()
}