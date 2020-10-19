package com.imassage.ui.fragment.mainPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.imassage.R
import com.imassage.databinding.FragmentMainPageBinding
import com.imassage.ui.base.ScopedFragment

class MainPageFragment : ScopedFragment() {
    private lateinit var viewModel: MainPageViewModel
    private lateinit var binding: FragmentMainPageBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        // TODO: Use the ViewModel
    }


}