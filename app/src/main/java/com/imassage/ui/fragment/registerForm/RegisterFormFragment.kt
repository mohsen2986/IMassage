package com.imassage.ui.fragment.registerForm

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.databinding.FragmentRegisterFormBinding
import com.imassage.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_register_form.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.io.File

class RegisterFormFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: RegisterFormViewModelFactory by instance()

    private lateinit var viewModel: RegisterFormViewModel
    private lateinit var binding: FragmentRegisterFormBinding
    private lateinit var navController: NavController
    // -- FOR DATA
    private lateinit var name: String
    private lateinit var family: String
    private lateinit var gender: String

    private lateinit var image: String
    private lateinit var fileUri: Uri
    private lateinit var postPath: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterFormBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEnterTransitions()
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(RegisterFormViewModel::class.java)
        getArgs()
        bindUI()
    }
    private  fun bindUI() = launch{
        setExitTransitions()
        fra_register_form_next.setOnClickListener{
            if(fra_signUp_second_mobile.text?.isNotEmpty()!! && fra_signUp_second_password.text?.isNotEmpty()!! && fra_signUp_second_confirm_password.text?.isNotEmpty()!!) {
                if(fra_signUp_second_mobile.text?.length == 10){
                    if(fra_signUp_second_password.text?.length!! >7){
                        if(fra_signUp_second_password.text.toString() == fra_signUp_second_confirm_password.text.toString()){
                            if (image == "true") {
                                registerIntoServerWithPhoto()
                            } else {
                                registerIntoServer()
                            }
                        } else { Toast.makeText(context , "پسورد ها برابر نیستند." , Toast.LENGTH_SHORT).show() }
                    }else { Toast.makeText(context , "طول پسورد بایستی از ۸ کاراکتر بیشتر باشد." , Toast.LENGTH_SHORT).show() }
                }else { Toast.makeText(context , "شماره تلفن راا بدون صفر وارد کنید." , Toast.LENGTH_SHORT).show() }
                hideKeyboard(it)
            }else{
                Toast.makeText(context , "مقادیر را وارد کنید." , Toast.LENGTH_SHORT).show()
            }
        }
        fra_register_form_back.setOnClickListener {
            hideKeyboard(it)
            requireActivity().onBackPressed()
        }
    }
    private fun registerIntoServer() = launch{
        when(val callback = viewModel.register(
                name ,
                family ,
                gender ,
                fra_signUp_second_mobile.text.toString() ,
                fra_signUp_second_password.text.toString() ,
                fra_signUp_second_confirm_password.text.toString())){
            is NetworkResponse.Success ->{
                navController.navigate(R.id.action_registerFormFragment_to_phoneVerificationFragment ,
                        bundleOf("verification_type" to "register")
                )
            }
            is NetworkResponse.ServerError ->{
                when(callback.code) {
                    422 -> Toast.makeText(context, "این شماره در سیستم وجود دارد.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun registerIntoServerWithPhoto() = launch{
        when(val callback = viewModel.register(
                name ,
                family ,
                gender ,
                fra_signUp_second_mobile.text.toString() ,
                fra_signUp_second_password.text.toString() ,
                fra_signUp_second_confirm_password.text.toString() , photo = imageBodyPart())){
            is NetworkResponse.Success ->{
                navController.navigate(R.id.action_registerFormFragment_to_phoneVerificationFragment ,
                        bundleOf("verification_type" to "register")
                )
            }
        }

    }
    private fun getArgs(){
        name = requireArguments().getString("name").toString()
        family = requireArguments().getString("family").toString()
        gender =requireArguments().getString("gender").toString()

        // image
        image = requireArguments().getString("image").toString()
        if(image == "true") {
            fileUri = requireArguments().get("fileUri") as Uri
            postPath = requireArguments().getString("postPath").toString()
        }

    }

    private fun setEnterTransitions() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = 500L
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = 500L
        }
    }

    private fun setExitTransitions() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = 500L
        }

        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = 500L
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    fun imageBodyPart(): MultipartBody.Part? {
        postPath?.let {
            if (it.isNotEmpty()){
                val imageFile = File(postPath!!)

                val requestBody = RequestBody.create(
                        requireActivity().contentResolver.getType(fileUri!!)?.toMediaTypeOrNull() ,
                        imageFile
                )
                val body = MultipartBody.Part.createFormData("photo", imageFile.name , requestBody)

//                val description_ = "we are fcosiety"
//                val description: RequestBody = RequestBody.create(
//                        MultipartBody.FORM, description_)

                return body
            }
        }
        return null
    }

}