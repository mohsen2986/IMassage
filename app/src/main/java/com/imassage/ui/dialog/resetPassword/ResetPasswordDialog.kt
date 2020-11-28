package com.imassage.ui.dialog.resetPassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.databinding.DialogResetPasswordBinding
import com.imassage.ui.base.ScopedDialogFragment
import com.imassage.ui.utils.OnCLickHandler
import kotlinx.android.synthetic.main.dialog_reset_password.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ResetPasswordDialog : ScopedDialogFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ResetPasswordViewModelFactory by instance()

    private lateinit var viewModel: ResetPasswordDialogViewModel
    private lateinit var navController: NavController
    private lateinit var binding: DialogResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DialogResetPasswordBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ResetPasswordDialogViewModel::class.java)
        uiActions()
    }
    private fun bindUI() = launch {
        when(val callback = viewModel.resetPassword(dialog_reset_password_phone.text.toString())){
            is NetworkResponse.Success -> {
                navController.navigate(R.id.action_resetPasswordDialog_to_resetPasswordFragment)
            }
        }
    }
    private fun uiActions(){
        binding.onClick = object :OnCLickHandler<Nothing> {
            override fun onClickItem(element: Nothing) {}
            override fun onClickView(view: View, element: Nothing) {}
            override fun onClick(view: View) {
                when(view){
                    dialog_reset_password_reset ->
                        bindUI()
                }
            }


        }
    }

}