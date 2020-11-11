package com.imassage.ui.fragment.reserve.packages

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.DataRepository

class PackagesViewModel(
        private val dataRepository: DataRepository
) : ViewModel() {

    suspend fun packages() =
        dataRepository.packages()
}