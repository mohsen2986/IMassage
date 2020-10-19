package com.imassage.ui.fragment.login

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.imassage.R
import com.imassage.databinding.FragmentLoginBinding
import com.imassage.ui.base.ScopedFragment

class LoginFragment : ScopedFragment() {
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        transitionAnimationEnter()
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