package com.imassage.ui.fragment.account

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.databinding.FragmentAccountBinding
import com.imassage.databinding.FragmentMainPageBinding
import com.imassage.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AccountFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: AccountViewModelFactory by instance()

    private lateinit var viewModel: AccountViewModel
    private lateinit var binding: FragmentAccountBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(AccountViewModel::class.java)
        UIActions()
        bindUI()
    }
    private fun bindUI() = launch{
        // get account information // todo add loading
        when(val callback = viewModel.accountInformation()){
            is NetworkResponse.Success -> {
                binding.account = callback.body
            }
        }
//        when(val callback = viewModel.updateAccount(name = "mohsen123" , family = null)){
//            is NetworkResponse.Success ->{
//                Log.e("Log__" , "the re")
//            }
//        }
    }
    private fun UIActions(){
        fra_account_name.setOnClickListener{
            navController.navigate(R.id.action_accountFragment_to_editAccountNameDialog)
        }
        fra_account_family.setOnClickListener{
            navController.navigate(R.id.action_accountFragment_to_editAccountFamilyDialog)
        }
        fra_account_gender.setOnClickListener{
            navController.navigate(R.id.action_accountFragment_to_editAccountGenderDialog)
        }
        fra_account_image.setOnClickListener{
            navController.navigate(R.id.action_accountFragment_to_editAccountImageDialog)
        }
        fra_account_back.setOnClickListener{
            activity!!.onBackPressed()
        }


    }

}