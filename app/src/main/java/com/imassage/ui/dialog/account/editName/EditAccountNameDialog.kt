package com.imassage.ui.dialog.account.editName

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.imassage.databinding.DialogEditNameBinding
import com.imassage.ui.base.ScopedDialogFragment
import kotlinx.android.synthetic.main.dialog_edit_name.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class EditAccountNameDialog(
): ScopedDialogFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: EditAccountNameDialogViewModelFactory by instance()

    private lateinit var viewModel: EditAccountNameViewModel
    private lateinit var binding: DialogEditNameBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DialogEditNameBinding.inflate(inflater , container , false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(EditAccountNameViewModel::class.java)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch {
    }
    private fun UIActions() {
        dialog_edit_name_submit.setOnClickListener{
            if(dialog_edit_name_name.text?.isNotEmpty()!!){
                sendNewName()
            }else{
                Toast.makeText(context , "اسم را وارد نکرده اید." , Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun sendNewName() = launch{
        viewModel.updateAccount(dialog_edit_name_name.text.toString())
        activity!!.onBackPressed()
    }

}