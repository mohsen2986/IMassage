package com.imassage.ui.fragment.registerForm

import androidx.lifecycle.ViewModel
import com.imassage.data.repository.UserInformationRepository

class RegisterFormViewModel(
        private val userInformationRepository: UserInformationRepository
) : ViewModel() {

    suspend fun register(name: String, family: String , gender: String , phone: String  , password: String , passwordConfirm: String) =
            userInformationRepository.register(name , family , phone , gender , password , passwordConfirm)

}