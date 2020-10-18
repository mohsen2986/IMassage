package com.imassage.ui.fragment.phoneVerification

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imassage.R

class PhoneVerificationFragment(
    ) : Fragment() {
    private lateinit var viewModel: PhoneVerificatoinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_phone_verificatoin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhoneVerificatoinViewModel::class.java)
        // TODO: Use the ViewModel
    }

}