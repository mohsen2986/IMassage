package com.imassage.data.repository

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.data.model.AccessToken
import com.imassage.data.remote.api.ApiInterface
import com.imassage.data.remote.model.ErrorResponse
import com.imassage.data.remote.model.SmsVerificationResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class UserInformationRepository(
        private val tokenRepository: TokenRepository ,
        private val apiInterface: ApiInterface
){
    companion object{
        private lateinit var smsToken: String
        private lateinit var password: String
    }
    suspend fun register(name: String , family: String, phone: String  , gender: String , password: String, passwordConfirm: String, photo: MultipartBody.Part?): NetworkResponse<SmsVerificationResponse, ErrorResponse> {
        if(photo != null){
            return withContext(IO) {
                Log.e("Log__" , "True")
                val callback = apiInterface.registerWithPhoto(photo , name, family , password, passwordConfirm , phone , gender == "true")
                when (callback){
                    is NetworkResponse.Success -> {
                        Companion.smsToken = callback.body.token
                        Companion.password = password
                    }
                }
                return@withContext callback
            }
        }
        else
            return withContext(IO) {
                Log.e("Log__" , "False")
            val callback = apiInterface.register(name, family , password, passwordConfirm , phone , gender == "true")
            when (callback){
                is NetworkResponse.Success -> {
                    Companion.smsToken = callback.body.token
                    Companion.password = password
                }
            }
            return@withContext callback
        }
    }
    suspend fun registerVerifySms(code: String): NetworkResponse<AccessToken, ErrorResponse> {
        Log.d("log__" , "name $smsToken" )
        Log.d("log__" , "pass $password" )
        Log.d("log__" , "name $code" )
        return withContext(IO){
            val callback = apiInterface.registerVerification(smsToken , code, password)
            when(callback){
                is NetworkResponse.Success -> {
                    tokenRepository.saveAccessToken(callback.body.accessToken)
                    tokenRepository.saveRefreshToken(callback.body.refreshToken)
                    tokenRepository.userLogin()
                }
            }
            return@withContext callback
        }
    }
    suspend fun login(phone: String , password: String) =
        withContext(IO){
            val callback = apiInterface.login(phone , password)
            when(callback){
                is NetworkResponse.Success ->{
                    Companion.smsToken = callback.body.token
                    Companion.password = password
                }
            }
            return@withContext callback
        }
    suspend fun loginVerifySms(code: String) =
            withContext(IO){
                val callback = apiInterface.loginVerification(smsToken , code , password)
                when(callback){
                    is NetworkResponse.Success -> {
                        tokenRepository.saveAccessToken(callback.body.accessToken)
                        tokenRepository.saveRefreshToken(callback.body.refreshToken)
                        tokenRepository.userLogin()
                    }
                }
                return@withContext callback
            }

    suspend fun resetPassword(phone: String): NetworkResponse<SmsVerificationResponse, ErrorResponse> {
        return withContext(IO){
            val callback = apiInterface.resetPassword(phone)
            when (callback){
                is NetworkResponse.Success -> {
                    Companion.smsToken = callback.body.token
                }
            }
            return@withContext callback
        }
    }

    suspend fun resetPasswordVerification(code: String , password: String) =
            apiInterface.verifyResetPassword(smsToken , code , password , password)


}