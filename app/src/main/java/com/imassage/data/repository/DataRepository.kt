package com.imassage.data.repository

import com.imassage.data.remote.api.AuthApiInterface
import com.imassage.ui.base.lazyDeferred

class DataRepository(
        private val apiInterface: AuthApiInterface
){
    // main page data
    suspend fun mainPage() =
            apiInterface.mainPage()

}