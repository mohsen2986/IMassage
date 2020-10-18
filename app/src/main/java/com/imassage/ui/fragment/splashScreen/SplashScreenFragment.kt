package com.imassage.ui.fragment.splashScreen

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialElevationScale
import com.imassage.R
import com.imassage.databinding.LoginFragmentBinding
import com.imassage.databinding.SplashScreenFragmentBinding
import kotlinx.coroutines.delay

class SplashScreenFragment : Fragment() {
    private lateinit var viewModel: SplashScreenViewModel
    private lateinit var binding: SplashScreenFragmentBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SplashScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)
        bindUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        transitionAnimation()

    }

    private fun bindUI() {

        // delay(1500)
        binding.fraSplashScreenImage.setOnClickListener {
            navController.navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }
    }

    private fun transitionAnimation() {
        exitTransition = MaterialElevationScale(false).apply {
            duration = 500L
        }

        AnimatorInflater.loadAnimator(context, R.animator.splash_screen_animation).apply {
            setTarget(binding.fraSplashScreenImage)
            start()
        }
    }
}