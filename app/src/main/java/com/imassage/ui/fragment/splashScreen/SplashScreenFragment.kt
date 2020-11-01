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
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialElevationScale
import com.imassage.R
import com.imassage.databinding.FragmentSplashScreenBinding
import com.imassage.ui.base.ScopedFragment
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SplashScreenFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: SplashScreenViewModelFactory by instance()

    private lateinit var viewModel: SplashScreenViewModel
    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        transitionAnimation()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(SplashScreenViewModel::class.java)
        checkLogin()
        bindUI()
    }


    private fun bindUI() {
    }

    // animations
    private fun transitionAnimation() {
        exitTransition = MaterialElevationScale(false).apply {
            duration = 500L
        }

        AnimatorInflater.loadAnimator(context, R.animator.splash_screen_animation).apply {
            setTarget(binding.fraSplashScreenImage)
            start()
        }
    }
    private fun checkLogin(){
        GlobalScope.launch(Main) {
            delay(1_500)
            if(viewModel.isLogin)
                navController.navigate(R.id.action_splashScreenFragment_to_mainPageFragment)
            else
                navController.navigate(R.id.action_splashScreenFragment_to_signUpFragment)
        }
    }
}