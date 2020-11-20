package com.imassage.ui.dialog.account.editGender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.imassage.R
import com.imassage.databinding.DialogEditGenderBinding
import com.imassage.databinding.DialogEditNameBinding
import com.imassage.ui.base.ScopedDialogFragment
import com.imassage.ui.dialog.account.editName.EditAccountNameDialogViewModelFactory
import com.imassage.ui.dialog.account.editName.EditAccountNameViewModel
import kotlinx.android.synthetic.main.dialog_edit_gender.*
import kotlinx.android.synthetic.main.dialog_edit_name.*
import kotlinx.android.synthetic.main.fragment_main_page.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class EditAccountGenderDialog(
): ScopedDialogFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: EditAccountGenderDialogViewModelFactory by instance()

    private lateinit var viewModel: EditAccountGenderDialogViewModel
    private lateinit var binding: DialogEditGenderBinding

    private lateinit var gender: String

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DialogEditGenderBinding.inflate(inflater , container , false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(EditAccountGenderDialogViewModel::class.java)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        getGender()
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch {
    }
    private fun UIActions() {
        dialog_edit_gender_submit.setOnClickListener{
            sendNewGender()
        }
    }
    private fun sendNewGender() = launch{
        viewModel.updateAccount(gender)
        activity!!.onBackPressed()
    }
    private fun getGender(){
        when(dialog_edit_gender_man_woman_group.checkedButtonId){
            R.id.dialog_edit_gender_woman -> gender = "false"
            R.id.dialog_edit_gender_man   -> gender = "true"
        }
        dialog_edit_gender_man_woman_group.addOnButtonCheckedListener{ group, checkedId, isChecked ->
            when(checkedId){
                R.id.dialog_edit_gender_woman -> gender = "false"
                R.id.dialog_edit_gender_man   -> gender = "true"
            }
        }
    }

}