package com.imassage.ui.fragment.signUp

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.imassage.R
import com.imassage.databinding.FragmentSignUpBinding
import com.imassage.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SignUpFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: SignUpViewModelFactory by instance()

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
//        transitionAnimationEnter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(SignUpViewModel::class.java)
        bindUI()
        uiActions()
    }
    private fun bindUI() = launch {
        Log.d("log__" , "enter the page")
    }
    private fun uiActions(){
        fra_signUp_man_woman_group.checkedButtonId
        fra_signUp_next.setOnClickListener{
            val gender = when(fra_signUp_man_woman_group.checkedButtonId){
                R.id.fra_signUp_man -> "true"
                else -> "false"  // for the other gender Females
            }
            val bundle = bundleOf(
                    "name" to fra_signUp_name.text.toString() ,
                    "family" to fra_signUp_family.text.toString() ,
                    "gender" to gender
            )
            navController.navigate(R.id.action_signUpFragment_to_registerFormFragment , bundle)
        }
        fra_signUp_go_to_login.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }



    private fun transitionAnimationEnter(){
        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fra_splashScreen_image)
            endView = binding.fraSignUpContainer
            duration = 500L
            scrimColor = Color.TRANSPARENT
            containerColor = R.drawable.back_test
            startContainerColor = R.drawable.back_test
            endContainerColor = R.drawable.back_test
        }
    }

}