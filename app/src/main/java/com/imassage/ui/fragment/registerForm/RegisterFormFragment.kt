package com.imassage.ui.fragment.registerForm

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterFormBinding.inflate(inflater , container , false)
        setEnterTransitions()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(RegisterFormViewModel::class.java)
        getArgs()
        bindUI()
    }
    private  fun bindUI() = launch{
        fra_phone_verification_next.setOnClickListener{
            registerIntoServer()
            setExitTransitions()
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
        }

    }
    private fun getArgs(){
        name = arguments!!.getString("name").toString()
        family = arguments!!.getString("family").toString()
        gender =arguments!!.getString("gender").toString()
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

}