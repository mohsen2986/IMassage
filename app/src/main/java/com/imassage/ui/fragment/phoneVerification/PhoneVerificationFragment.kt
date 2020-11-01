package com.imassage.ui.fragment.phoneVerification

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_phone_verificatoin.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PhoneVerificationFragment: ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private  val viewModelFactory: PhoneVerificationViewModelFactory by instance()

    private lateinit var viewModel: PhoneVerificationViewModel
    private lateinit var navController: NavController
    // -- FOR DATA
    private lateinit var verificationType: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_phone_verificatoin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(PhoneVerificationViewModel::class.java)
        getArgs()
        bindUI()
    }
    private fun bindUI() = launch {
        fra_phone_verification_next.setOnClickListener{
            sendCode()
        }
        textListeners()
    }
    private fun sendCode() = launch {
        val code: String  = fra_phone_verification_digit_one.text.toString() + fra_phone_verification_digit_two.text.toString() +
                fra_phone_verification_digit_three.text.toString() + fra_phone_verification_digit_four.text.toString()
        when (viewModel.sendVerificationCode(code , verificationType)){
            is NetworkResponse.Success ->{
                navController.navigate(R.id.action_phoneVerificationFragment_to_mainPageFragment)
            }
        }
    }
    private fun getArgs(){
        verificationType = arguments!!.getString("verification_type").toString()
        Log.d("log__" , "the arg is $verificationType")

    }

    private fun textListeners() {
        fra_phone_verification_digit_one.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length == 1){
                    fra_phone_verification_digit_two.requestFocus()
                }
            }
        })

        fra_phone_verification_digit_two.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length == 1){
                    fra_phone_verification_digit_three.requestFocus()
                }
            }
        })

        fra_phone_verification_digit_three.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length == 1){
                    fra_phone_verification_digit_four.requestFocus()
                }
            }
        })
    }

}