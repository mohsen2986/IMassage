package com.imassage.data.remote.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.data.remote.model.ErrorResponse
import com.imassage.data.remote.model.MainPageResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiInterface{

    // get mainPage
    @POST("mainPage")
    suspend fun mainPage(
    ): NetworkResponse<MainPageResponse , ErrorResponse>

}