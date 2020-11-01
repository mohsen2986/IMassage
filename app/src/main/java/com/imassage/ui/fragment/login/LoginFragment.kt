package com.imassage.ui.fragment.login

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.databinding.FragmentLoginBinding
import com.imassage.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_login.*
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        transitionAnimationEnter()
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
            activity!!.onBackPressed()
        }
        fra_login_LoginButton.setOnClickListener{
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


    private fun transitionAnimationEnter(){
        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fra_splashScreen_image)
            endView = binding.fraLoginContainer
            duration = 500L
            scrimColor = Color.TRANSPARENT
        }
    }
}