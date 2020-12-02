package com.imassage.data.repository

import android.util.Log
import android.widget.Toast
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.data.model.MainPage
import com.imassage.data.remote.api.AuthApiInterface
import com.imassage.data.remote.model.AnswerRequest
import com.imassage.data.remote.model.MainPageResponse
import com.imassage.ui.base.lazyDeferred

class DataRepository(
        private val apiInterface: AuthApiInterface
){
    // main page data
    val mainPage by lazyDeferred {
        when(val callback = apiInterface.mainPage()){
            is NetworkResponse.Success -> callback.body.datas
            else -> MainPage(listOf() , listOf() , listOf())
        }
    }
    // packages data
    val packages by lazyDeferred {
        when(val callback = apiInterface.packages()){
            is NetworkResponse.Success -> callback.body.datas
            else -> listOf()
        }
    }
    suspend fun mainPage() =
            apiInterface.mainPage()

    suspend fun massages() =
            apiInterface.massages()

    suspend fun packages() =
            apiInterface.packages()

    suspend fun questions() =
            apiInterface.questions()

    suspend fun answer(answers:AnswerRequest) =
            apiInterface.answers(answers)

    // update account

    // send consulting
    suspend fun consulting() =
            apiInterface.consulting()

}