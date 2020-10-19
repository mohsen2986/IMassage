package com.imassage.ui.fragment.signUp

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.imassage.R
import com.imassage.databinding.FragmentSignUpBinding
import com.imassage.ui.base.ScopedFragment

class SignUpFragment : ScopedFragment() {
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
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
            endView = binding.fraSignUpContainer
            duration = 500L
            scrimColor = Color.TRANSPARENT
            containerColor = R.drawable.splash_background
            startContainerColor = R.drawable.splash_background
            endContainerColor = R.drawable.splash_background
        }
    }

}