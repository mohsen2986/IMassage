package com.imassage.ui.fragment.receipt

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.data.model.Package
import com.imassage.data.model.Transactions
import com.imassage.databinding.FragmentReceiptBinding
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.OnCLickHandler
import com.zarinpal.ewallets.purchase.ZarinPal
import kotlinx.android.synthetic.main.fragment_receipt.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ReceiptFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ReceiptViewModelFactory by instance()

    private lateinit var viewModel: ReceiptViewModel
    private lateinit var binding: FragmentReceiptBinding
    private lateinit var navController: NavController
    // -- FOR DATA
    private lateinit var transactions : Transactions

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentReceiptBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ReceiptViewModel::class.java)
        bindUI()
        UIActions()
    }
    private fun bindUI() = launch {
        when(val callback = viewModel.transaction()){
            is NetworkResponse.Success ->{
                callback.body?.let {
                    transactions = callback.body
                    viewModel.setTransaction(transactions.id)
                    binding.transaction = transactions
                    binding.gender = viewModel.getGender().toString()
                    binding.time = viewModel.getTime()
                    binding.date = viewModel.getDate()
                    binding.massage = viewModel.massage()
                    binding.packages = viewModel.packages()
                }
            }
        }
        binding.account = viewModel.account().await()
    }
    private fun UIActions(){
        binding.onClick = object: OnCLickHandler<Nothing>{
            override fun onClickItem(element: Nothing) {}
            override fun onClickView(view: View, element: Nothing) {}
            override fun onClick(view: View) {
                when(view){
                    fra_question_back ->
                        requireActivity().onBackPressed()
                }
            }


        }
        fra_reserved_time_go_to_bank.setOnClickListener{
            startPayment(transactions.amountWithOffer.toInt())
        }
        fra_receipt_next.setOnClickListener{
            if(fra_receipt_off_code.text?.isNotEmpty()!!){
                sendOfferCode(fra_receipt_off_code.text.toString())
            }
        }
    }
    private fun startPayment( cost :Int){
        val purchase = ZarinPal.getPurchase(activity)
        val payment = ZarinPal.getSandboxPaymentRequest()
        payment.merchantID = "71c705f8-bd37-11e6-aa0c-000c295eb8fc"
        payment.description = "صحفه پرداخت IMassage"
        payment.amount = cost.toLong()
        payment.setCallbackURL("payment://imassage/")
        purchase.startPayment(payment){
            status, authority, paymentGatewayUri, intent ->
            if (status == 100) {
                activity!!.startActivity(intent)
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(context!!, paymentGatewayUri)
            }
            else{
                Toast.makeText(context, "خطا در ایجاد درخواست پرداخت" , Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun sendOfferCode(offerCode:String) = launch{
        when(val callback = viewModel.makeOffer(transactions.id , offerCode)){
            is NetworkResponse.Success ->{
                if(callback.code == 200)
                    binding.transaction = callback.body
                else
                    Toast.makeText(requireContext() , "کد وارد شده معتبر نمیباشد." , Toast.LENGTH_SHORT).show()
            }
            is NetworkResponse.ServerError ->
                Toast.makeText(requireContext() , "کد وارد شده معتبر نمیباشد." , Toast.LENGTH_SHORT).show()
        }
    }

}