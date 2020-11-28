package com.imassage.ui.fragment.resetPassword

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.databinding.FragmentResetPasswordBinding
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.OnCLickHandler
import kotlinx.android.synthetic.main.fragment_phone_verificatoin.*
import kotlinx.android.synthetic.main.fragment_reset_password.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ResetPasswordFragment : ScopedFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ResetPasswordViewModelFactory by instance()

    private lateinit var viewModel: ResetPasswordViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater , container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ResetPasswordViewModel::class.java)
        uiActions()
    }
    private fun bindUI() =launch {
        when(val callback = viewModel.sendCode(getCode() , fra_new_password_password.text.toString())){
            is NetworkResponse.Success -> {
                if(callback.code == 200)
                    navController.navigate(R.id.action_resetPasswordFragment_to_splashScreenFragment)
            }
        }
        textListeners()
    }
    private fun uiActions(){
        binding.onClick = object : OnCLickHandler<Nothing>{
            override fun onClickItem(element: Nothing) {}
            override fun onClickView(view: View, element: Nothing) {}
            override fun onClick(view: View) {
                when(view){
                    fra_new_password_submit -> {
                        hideKeyboard(view)
                        bindUI()
                    }
                }
            }


        }
    }
    private fun getCode() =
            fra_reset_password_digit_one.text.toString() + fra_reset_password_digit_two.text.toString() +
                    fra_reset_password_digit_three.text.toString() + fra_reset_password_digit_four.text.toString()


    private fun textListeners() {
        fra_reset_password_digit_one.addTextChangedListener(object : TextWatcher {
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

        fra_reset_password_digit_two.addTextChangedListener(object : TextWatcher {
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

        fra_reset_password_digit_three.addTextChangedListener(object : TextWatcher {
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

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}