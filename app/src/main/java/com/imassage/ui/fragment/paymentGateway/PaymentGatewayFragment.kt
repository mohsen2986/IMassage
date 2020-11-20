package com.imassage.ui.fragment.paymentGateway

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.databinding.FragmentPaymentGatewayBinding
import com.imassage.ui.base.ScopedFragment
import com.zarinpal.ewallets.purchase.ZarinPal
import kotlinx.android.synthetic.main.fragment_payment_gateway.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PaymentGatewayFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: PaymentGatewayViewModelFactory by instance()

    private lateinit var viewModel: PaymentGatewayViewModel
    private lateinit var binding: FragmentPaymentGatewayBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentGatewayBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(PaymentGatewayViewModel::class.java)
        initPayment()
        UIActions()
        bindUI()
    }
    private fun bindUI() = launch{
    }

    private fun initPayment(){
        val data = activity!!.intent.data
        ZarinPal.getPurchase(context).verificationPayment(data){
                isPaymentSuccess, refID, paymentRequest ->
            if(isPaymentSuccess){
                binding.refCode = refID
                binding.isSuccess = true
                sendTransactionResponse(refID)
            }else{
                binding.isSuccess = false
            }
        }
    }
    private fun sendTransactionResponse(refId: String) = launch{
        when(val callback = viewModel.payTransaction(refId)){
            is NetworkResponse.Success ->{
                sendOrder()
            }
        }
    }
    private fun sendOrder() = launch {
        viewModel.sendOrder()
    }
    private fun UIActions(){
        fra_payment_back_to_home.setOnClickListener{
            activity!!.onBackPressed()
        }
    }
}