package com.imassage.data.remote

import com.imassage.data.remote.api.AuthApiInterface
import com.imassage.data.repository.TokenRepository
import retrofit2.Retrofit

class CreateAuthApiInterface(
    private val retrofit: Retrofit ,
    private val tokenInterceptor: TokenInterceptor
){
    operator fun invoke(): AuthApiInterface {
        return retrofit.newBuilder().client(tokenInterceptor.invoke())
            .build().create(AuthApiInterface::class.java)
    }
}