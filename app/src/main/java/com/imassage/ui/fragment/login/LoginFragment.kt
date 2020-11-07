package com.imassage.ui.fragment.login

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.ViewGroupCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialElevationScale
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.databinding.FragmentLoginBinding
import com.imassage.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class LoginFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: LoginViewModelFactory by instance()

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding : FragmentLoginBinding
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setEnterTransitions()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(LoginViewModel::class.java)
        bindUI()
        onClickListeners()
    }

    private fun bindUI() = launch {

    }
    private fun onClickListeners(){
        fra_login_back.setOnClickListener{
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
                duration = 500L
            }
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
                duration = 500L
            }
            hideKeyboard(it)
            activity!!.onBackPressed()
        }
        fra_login_LoginButton.setOnClickListener{
            hideKeyboard(it)
            exitTransition =  MaterialSharedAxis(MaterialSharedAxis.X,true).apply {
                duration = 500L
            }
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X,false).apply {
                duration = 500L
            }
            loginIntoServer()
        }
    }
    private fun loginIntoServer() = launch {
        val phone = fra_login_mobile.text.toString()
        val password = fra_login_password.text.toString()
        when(val calback = viewModel.login(phone , password)){
            is NetworkResponse.Success -> {
                navController.navigate(R.id.action_loginFragment_to_phoneVerificationFragment ,
                        bundleOf("verification_type" to "login")
                )
            }
        }
    }


    private fun hideKeyboard(view: View) {
        val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setEnterTransitions() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = 500L
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = 500L
        }
    }
}