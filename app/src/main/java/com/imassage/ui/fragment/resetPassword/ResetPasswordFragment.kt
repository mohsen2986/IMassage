package com.imassage.ui.fragment.resetPassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.databinding.FragmentResetPasswordBinding
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.OnCLickHandler
import kotlinx.android.synthetic.main.fragment_reset_password.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ResetPasswordFragment : ScopedFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ResetPasswordViewModelFactory by instance()

    private lateinit var viewModel: ResetPasswordViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentResetPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(inflater , container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ResetPasswordViewModel::class.java)
        uiActions()
    }
    private fun bindUI() =launch {
        when(val callback = viewModel.sendCode(getCode() , fra_new_password_password.text.toString())){
            is NetworkResponse.Success -> {
                if(callback.code == 200)
                    navController.navigate(R.id.action_resetPasswordFragment_to_splashScreenFragment)
            }
        }
    }
    private fun uiActions(){
        binding.onClick = object : OnCLickHandler<Nothing>{
            override fun onClickItem(element: Nothing) {}
            override fun onClickView(view: View, element: Nothing) {}
            override fun onClick(view: View) {
                when(view){
                    fra_new_password_submit ->
                        bindUI()
                }
            }


        }
    }
    private fun getCode() =
            fra_reset_password_digit_one.text.toString() + fra_reset_password_digit_two.text.toString() +
                    fra_reset_password_digit_three.text.toString() + fra_reset_password_digit_four.text.toString()


}