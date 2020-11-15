package com.imassage.data.remote.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.data.model.Account
import com.imassage.data.model.Form
import com.imassage.data.remote.model.*
import retrofit2.http.*

interface AuthApiInterface{

    // get mainPage
    @POST("mainPage")
    suspend fun mainPage(
    ): NetworkResponse<MainPageResponse , ErrorResponse>

    // get Massages
    @GET("massage")
    suspend fun massages(
    ): NetworkResponse<MassageResponse, ErrorResponse>

    // get packages
    @GET("packages")
    suspend fun packages(
    ): NetworkResponse<PackageResponse, ErrorResponse>

    // send answers
    @POST("questions")
    suspend fun answers(
            @Body answers: AnswerRequest
    ): NetworkResponse<Form, ErrorResponse>

    // update user information
    @PUT("user_info/1")
    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    suspend fun updateAccount(
        @Query("name") name: String? ,
        @Query("family") family: String?
    ):NetworkResponse<Account, ErrorResponse>
}