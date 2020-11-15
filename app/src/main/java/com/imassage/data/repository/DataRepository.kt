package com.imassage.data.repository

import com.imassage.data.remote.api.AuthApiInterface
import com.imassage.data.remote.model.AnswerRequest
import com.imassage.ui.base.lazyDeferred

class DataRepository(
        private val apiInterface: AuthApiInterface
){
    // main page data
    suspend fun mainPage() =
            apiInterface.mainPage()

    suspend fun massages() =
            apiInterface.massages()

    suspend fun packages() =
            apiInterface.packages()

    suspend fun answer(answers:AnswerRequest) =
            apiInterface.answers(answers)

    // update account
    suspend fun updateAccount(name:String? , family: String?) =
            apiInterface.updateAccount(name , family)
}