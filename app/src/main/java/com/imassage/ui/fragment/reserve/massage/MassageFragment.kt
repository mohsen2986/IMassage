package com.imassage.ui.fragment.reserve.massage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imassage.R

class MassageFragment : Fragment() {

    companion object {
        fun newInstance() = MassageFragment()
    }

    private lateinit var viewModel: MassageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_massage, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MassageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}