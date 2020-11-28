package com.imassage.data.remote.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.data.model.AccessToken
import com.imassage.data.remote.model.ErrorResponse
import com.imassage.data.remote.model.SmsVerificationResponse
import com.imassage.data.remote.model.Status
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiInterface{
    // register
    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name")
        name: String ,
        @Field("family")
        family: String ,
        @Field("password")
        password: String ,
        @Field("password_confirmation")
        passwordConfirmation: String ,
        @Field("phone")
        phone: String ,
        @Field("gender")
        gender: Boolean
    ): NetworkResponse<SmsVerificationResponse, ErrorResponse>

    // register
    @Multipart
    @POST("register")
    suspend fun registerWithPhoto(
            @Part photo: MultipartBody.Part,
            @Query("name")
            name: String,
            @Query("family")
            family: String,
            @Query("password")
            password: String,
            @Query("password_confirmation")
            passwordConfirmation: String,
            @Query("phone")
            phone: String,
            @Query("gender")
            gender: Boolean
    ): NetworkResponse<SmsVerificationResponse, ErrorResponse>

    @POST("registerVerify")
    @FormUrlEncoded
    suspend fun registerVerification(
        @Field("token")
        token: String ,
        @Field("code")
        code: String,
        @Field("password")
        password: String
    ): NetworkResponse<AccessToken , ErrorResponse>

    // login
    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("phone")
        phone: String ,
        @Field("password")
        password: String
    ): NetworkResponse<SmsVerificationResponse , ErrorResponse>

    @POST("loginVerify")
    @FormUrlEncoded
    suspend fun loginVerification(
        @Field("token")
        token: String,
        @Field("code")
        code: String ,
        @Field("password")
        password: String
    ): NetworkResponse<AccessToken , ErrorResponse>

    // logout
    @POST("logout")
    suspend fun logOut(
    ): NetworkResponse< Nothing , ErrorResponse>

    // refresh token
    @POST("refresh")
    @FormUrlEncoded
    suspend fun refreshToken(
        @Field("refresh_token")
        refreshToke: String
    ): NetworkResponse<AccessToken , ErrorResponse>

    // reset password
    @POST("resetPassword")
    suspend fun resetPassword(
            @Query("phone") phone: String
    ): NetworkResponse< SmsVerificationResponse , ErrorResponse>

    // verify reset password
    @POST("verifyResetPassword")
    suspend fun verifyResetPassword(
            @Query("token") token: String ,
            @Query("code") code: String ,
            @Query("password") password: String ,
            @Query("password_confirmation") passwordConfirmation: String
    ):NetworkResponse< Status , ErrorResponse>
}