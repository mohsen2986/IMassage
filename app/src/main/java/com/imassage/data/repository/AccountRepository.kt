package com.imassage.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry
import com.imassage.data.model.Account
import com.imassage.data.model.MainPage
import com.imassage.data.remote.api.AuthApiInterface
import com.imassage.ui.base.lazyDeferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class AccountRepository(
        private val apiInterface: AuthApiInterface
){
    // get Account information
    // TODO GET ACCOUNT INFORMATION
    suspend fun userInformation() =
            apiInterface.userInformation()
    
    // update Account information
    suspend fun updateAccount(name:String? , family: String? , gender: String? , address: String? , birthday: String?) =
            apiInterface.updateAccount(name , family , gender , address , birthday )

    val account by lazyDeferred {
        when(val callback = apiInterface.userInformation()){
            is NetworkResponse.Success -> callback.body
            else -> Account("unknown" , "unknown" ,"unknown" , "unknown" , "true" , "unknown" , "1399/9/9")
        }
    }

    suspend fun updatePhotoAccount(photo: MultipartBody.Part) =
            apiInterface.updatePhotoAccount(photo)

    // send consulting
    suspend fun consulting() =
            apiInterface.consulting()


}