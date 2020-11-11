package com.imassage.ui.fragment.reserve.massage

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
import com.imassage.data.model.Massage
import com.imassage.databinding.FragmentMassageBinding
import com.imassage.ui.adapter.recyclerView.RecyclerAdapter
import com.imassage.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_massage.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MassageFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: MassageViewModelFactory by instance()

    private lateinit var viewModel: MassageViewModel
    private lateinit var binding: FragmentMassageBinding
    private lateinit var navController: NavController
    // -- FOR DATA
    private lateinit var massagesAdapter: RecyclerAdapter<Massage>

    override fun onCreateView(
            inflater: LayoutInflater
            , container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = FragmentMassageBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(MassageViewModel::class.java)
        initAdapter()
        bindAdapter()
        bindUI()
    }

    private fun bindUI() = launch{
        when(val data = viewModel.massages()){
            is NetworkResponse.Success ->{
                Log.e("Log__" , "the massages is ${data.body.datas}")
                massagesAdapter.datas = data.body.datas
                delay(2_000)
                navController.navigate(R.id.action_massageFragment_to_packageFragment);
            }
        }
    }
    private fun initAdapter(){
        massagesAdapter = RecyclerAdapter()
    }
    private fun bindAdapter(){
        fra_massage_rv.apply {
            adapter = massagesAdapter
        }
    }

}