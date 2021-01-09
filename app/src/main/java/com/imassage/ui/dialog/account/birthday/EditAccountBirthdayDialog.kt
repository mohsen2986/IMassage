package com.imassage.ui.dialog.account.birthday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.databinding.DialogEditBirthdayBinding
import com.imassage.ui.base.ScopedDialogFragment
import com.imassage.ui.utils.StaticVariables
import com.wdullaer.materialdatetimepicker.JalaliCalendar
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.dialog_edit_birthday.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class EditAccountBirthdayDialog(
): ScopedDialogFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: EditAccountBirthdayDialogViewModelFactory by instance()

    private lateinit var viewModel: EditAccountBirthdayDialogViewModel
    private lateinit var binding: DialogEditBirthdayBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = DialogEditBirthdayBinding.inflate(inflater , container  , false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(EditAccountBirthdayDialogViewModel::class.java)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch {
    }
    private fun UIActions() {
        dialog_edit_birthday_submit.setOnClickListener{
            if(dialog_edit_birthday_birthday.text?.isNotEmpty()!!){
                sendNewBirthday()
            }else{
                Toast.makeText(context , "اسم را وارد نکرده اید." , Toast.LENGTH_SHORT).show()
            }
        }
        dialog_edit_birthday_birthday.setOnClickListener{
            getBirthday()
        }
    }
    private fun sendNewBirthday() = launch{
        when(viewModel.updateBirthday(birthday = dialog_edit_birthday_birthday.text.toString())){
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
    private fun getBirthday(){
        val calendarType: DatePickerDialog.Type = DatePickerDialog.Type.JALALI
        val now: JalaliCalendar = JalaliCalendar.getInstance()
        val dp = DatePickerDialog.newInstance(
                calendarType,
                { view, year, monthOfYear, dayOfMonth ->
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )
        dp.onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            "$year-${monthOfYear+1}-$dayOfMonth".let {
                dialog_edit_birthday_birthday.setText(it)
            }
        }
        dp.show(requireActivity().supportFragmentManager, "DatePickerDialog")
    }
}