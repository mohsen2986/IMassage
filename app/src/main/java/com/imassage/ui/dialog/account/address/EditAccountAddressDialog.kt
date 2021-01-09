package com.imassage.ui.dialog.account.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.databinding.DialogEditAccountAddressBinding
import com.imassage.databinding.DialogEditAddressBinding
import com.imassage.databinding.DialogEditFamilyBinding
import com.imassage.databinding.DialogEditNameBinding
import com.imassage.ui.base.ScopedDialogFragment
import com.imassage.ui.dialog.account.editFamily.EditAccountFamilyDialogViewModel
import com.imassage.ui.dialog.account.editFamily.EditAccountFamilyDialogViewModelFactory
import com.imassage.ui.dialog.account.editName.EditAccountNameDialogViewModelFactory
import com.imassage.ui.dialog.account.editName.EditAccountNameViewModel
import com.imassage.ui.fragment.reserve.massage.MassageViewModel
import com.imassage.ui.utils.StaticVariables
import kotlinx.android.synthetic.main.dialog_edit_address.*
import kotlinx.android.synthetic.main.dialog_edit_family.*
import kotlinx.android.synthetic.main.dialog_edit_name.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class EditAccountAddressDialog(
): ScopedDialogFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: EditAccountAddressDialogViewModelFactory by instance()

    private lateinit var viewModel: EditAccountAddressDialogViewModel
    private lateinit var binding: DialogEditAddressBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DialogEditAddressBinding.inflate(inflater , container  , false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(EditAccountAddressDialogViewModel::class.java)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch {
    }
    private fun UIActions() {
        dialog_edit_address_submit.setOnClickListener{
            if(dialog_edit_address_address.text?.isNotEmpty()!!){
                sendNewAddress()
            }else{
                Toast.makeText(context , "اسم را وارد نکرده اید." , Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun sendNewAddress() = launch{
        when(viewModel.updateAccount(address = dialog_edit_address_address.text.toString())){
            is NetworkResponse.Success ->{
                refreshDate()
                requireActivity().onBackPressed()
            }
        }
    }
    private fun refreshDate(){
        setFragmentResult("requestKey", bundleOf("bundleKey" to StaticVariables.REFRESH))
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
//                 Handle the back button event
                if (isEnabled) {
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}