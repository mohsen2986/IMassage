package com.imassage.ui.fragment.reserve.packages

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
import com.imassage.databinding.FragmentPackagesBinding
import com.imassage.ui.adapter.recyclerView.RecyclerAdapter
import com.imassage.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_packages.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import com.imassage.data.model.Package
class PackagesFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: PackagesViewModelFactory by instance()

    private lateinit var viewModel: PackagesViewModel
    private lateinit var binding: FragmentPackagesBinding
    private lateinit var navController: NavController

    // -- FOR DATA
    private lateinit var packageAdapter: RecyclerAdapter<Package>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPackagesBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(PackagesViewModel::class.java)
        initAdapter()
        bindAdapter()
        bindUI()
    }
    private fun bindUI() = launch{
        when(val callback = viewModel.packages()){
            is NetworkResponse.Success -> {
                Log.e("Log__" , "the data packages are ${callback.body.datas}")
                packageAdapter.datas = callback.body.datas
            }
        }
    }
    private fun initAdapter(){
        packageAdapter = RecyclerAdapter()
    }
    private fun bindAdapter(){
//        fra_package_rv.apply{
//            adapter = packageAdapter
//        }
    }
}