package com.imassage.ui.fragment.reserve.packages

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
import com.imassage.R
import com.imassage.data.model.Answer
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
import com.imassage.data.remote.model.AnswerRequest
import com.imassage.ui.utils.OnCLickHandler
import com.imassage.ui.utils.StaticVariables
import kotlinx.android.synthetic.main.fragment_massage.*
import kotlinx.coroutines.delay

class PackagesFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: PackagesViewModelFactory by instance()

    private lateinit var viewModel: PackagesViewModel
    private lateinit var binding: FragmentPackagesBinding
    private lateinit var navController: NavController

    // -- FOR DATA
    private lateinit var packageAdapter: RecyclerAdapter<Package>
    private lateinit var massageId: String
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
        getArgument()
        initAdapter()
        bindAdapter()
        UIActions()
        bindUI()
    }
    private fun bindUI() = launch{
        viewModel.packages().await().let {
            packageAdapter.datas = it.filter { p: Package -> p.massageId == massageId }
        }
//        delay(3__000)
//        navController.navigate(R.id.action_packageFragment_to_reservedTimeFragment)
//        when(val callback = viewModel.packages()){
//            is NetworkResponse.Success -> {
//                Log.e("Log__" , "the data packages are ${callback.body.datas}")
//                packageAdapter.datas = callback.body.datas
//                delay(2__000)
//                navController.navigate(R.id.action_packageFragment_to_questionFragment)
//            }
//        }
    }
    private fun initAdapter(){
        packageAdapter = RecyclerAdapter()
        packageAdapter.onClickHandler = object : OnCLickHandler<Package> {
            override fun onClickItem(element: Package) {
                viewModel.packageId(element.packageId)
                viewModel.Package_(element)
                navController.navigate(R.id.action_packageFragment_to_reservedTimeFragment)
            }

            override fun onClick(view: View) {}
            override fun onClickView(view: View, element: Package) {}

        }
    }
    private fun bindAdapter(){
        fra_package_recycler.apply {
            adapter = packageAdapter
        }
    }
    private fun getArgument(){
        massageId = arguments!!.getString(StaticVariables.MASSAGE_ID).toString()
    }
    private fun UIActions(){
        fra_package_back.setOnClickListener{
            activity!!.onBackPressed()
        }
    }
}