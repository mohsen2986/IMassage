package com.imassage.ui.fragment.registerForm

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.UserInformationRepository
import okhttp3.MultipartBody

class RegisterFormViewModel(
        private val userInformationRepository: UserInformationRepository
) : ViewModel() {

    suspend fun register(name: String, family: String , gender: String , phone: String  , password: String , passwordConfirm: String) =
            userInformationRepository.register(name , family , phone , gender , password , passwordConfirm , null)

    suspend fun register(name: String, family: String , gender: String , phone: String  , password: String , passwordConfirm: String , photo: MultipartBody.Part?) =
            userInformationRepository.register(name , family , phone , gender , password , passwordConfirm , photo)

}