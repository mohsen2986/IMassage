package com.imassage.ui.fragment.signUpForm

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.databinding.FragmentSignUpBinding
import com.imassage.databinding.FragmentSignUpFormBinding
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.OnCLickHandler
import com.imassage.ui.utils.StaticVariables
import com.wdullaer.materialdatetimepicker.JalaliCalendar
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_sign_up_form.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class SignUpFormFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: SingUpFormViewModelFactory by instance()

    private lateinit var viewModel: SignUpFormViewModel
    private lateinit var binding: FragmentSignUpFormBinding
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = FragmentSignUpFormBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(SignUpFormViewModel::class.java)
        bindUI()
        uiActions()
    }
    private fun bindUI() = launch {
    }
    private fun uiActions(){
        binding.onClick = object: OnCLickHandler<Nothing> {
            override fun onClickItem(element: Nothing) {}
            override fun onClickView(view: View, element: Nothing) {}
            override fun onClick(view: View) {
                when(view){
                    fra_signUp_Form_date->{
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
                            "$year-$monthOfYear-$dayOfMonth".let {
                                fra_signUp_Form_date.setText(it)
                            }
                        }
                        dp.show(requireActivity().supportFragmentManager, "DatePickerDialog")
                    }
                    fra_signUp_Form_next ->{
                        if(fra_signUp_Form_check_box.isChecked)
                            sendConsulting()
                        sendAddressAndBirthDay()
                    }
                }
            }
        }
    }
    private fun sendConsulting() = launch {
        viewModel.consulting()
    }
    private fun sendAddressAndBirthDay() = launch {
        if(fra_signUp_Form_date.text?.isNotEmpty()!! && fra_signUp_Form_address.text?.isNotEmpty()!!){
            when(viewModel.sendAddressAndBirthday(fra_signUp_Form_date.text.toString(), fra_signUp_Form_address.text.toString())){
                is NetworkResponse.Success ->{
                    navController.navigate(R.id.action_signUpFormFragment_to_mainPageFragment ,
                            bundleOf(StaticVariables.SOURCE_FRAGMENT to StaticVariables.SIGN_UP_FORM))
                }
            }
        }else{
            Toast.makeText(requireContext() , "همه فیلد ها را پر کنید.", Toast.LENGTH_SHORT).show()
        }
    }

}