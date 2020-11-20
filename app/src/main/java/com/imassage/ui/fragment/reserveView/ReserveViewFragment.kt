package com.imassage.ui.fragment.reserveView

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.data.model.Order
import com.imassage.data.remote.model.OrdersResponse
import com.imassage.databinding.FragmentReserveViewBinding
import com.imassage.ui.adapter.recyclerView.RecyclerAdapter
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.StaticVariables
import kotlinx.android.synthetic.main.fragment_massage.*
import kotlinx.android.synthetic.main.fragment_reserve_view.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ReserveViewFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory : ReserveViewViewModelFactory by instance()

    private lateinit var viewModel: ReserveViewViewModel
    private lateinit var binding: FragmentReserveViewBinding
    private lateinit var navController: NavController

    // -- FOR DATA
    private lateinit var orderAdapter: RecyclerAdapter<Order>
    private var reserveType = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReserveViewBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ReserveViewViewModel::class.java)
        getArgument()
        initAdapters()
        bindAdapters()
        UIAction()
        bindUI()
    }
    private fun bindUI() = launch{
        when(reserveType) {
            StaticVariables.RESERVE_TIME -> {
                when (val callback = viewModel.reserves()) {
                    is NetworkResponse.Success -> {
                        orderAdapter.datas = callback.body.datas
                    }
                    else -> {
                        Toast.makeText(context , "عدم ارتباط با سرور." , Toast.LENGTH_SHORT).show()
                    }
                }
            }
            StaticVariables.HISTORY -> {
                when (val callback = viewModel.ordersHistory()) {
                    is NetworkResponse.Success -> {
                        orderAdapter.datas = callback.body.datas
                    }
                    else -> {
                        Toast.makeText(context , "عدم ارتباط با سرور." , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun getArgument(){
        reserveType = arguments!!.getString(StaticVariables.RESERVE_TYPE).toString()
    }
    private fun initAdapters(){
        orderAdapter = RecyclerAdapter()
    }
    private fun bindAdapters(){
        fra_reserve_view_recycler.apply{
            adapter = orderAdapter
        }
    }
    private fun UIAction(){
        fra_reserve_view_back.setOnClickListener{
            activity!!.onBackPressed()
        }
    }

}