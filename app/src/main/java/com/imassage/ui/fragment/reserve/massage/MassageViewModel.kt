package com.imassage.ui.fragment.reserve.massage

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.DataRepository

class MassageViewModel(
    private val dataRepository: DataRepository
    ) : ViewModel() {

    suspend fun massages() =
            dataRepository.massages()
    suspend fun tesxt() =
            dataRepository.mainPage()
}