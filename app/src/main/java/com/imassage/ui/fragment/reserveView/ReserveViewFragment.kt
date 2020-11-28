package com.imassage.ui.fragment.reserveView

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.data.model.Order
import com.imassage.data.remote.model.NetworkState
import com.imassage.data.remote.model.OrdersResponse
import com.imassage.databinding.FragmentReserveViewBinding
import com.imassage.ui.adapter.paging.RecyclerAdapterPaging
import com.imassage.ui.adapter.recyclerView.RecyclerAdapter
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.OnCLickHandler
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
    // -- FOR DATA
    private lateinit var adapter: RecyclerAdapterPaging<Any>

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
//        configViewModel(requireArguments().getString(StaticVariables.RESERVE_TYPE).toString())
//        configureObservables()
//        initAdapter()
//        fra_reserve_view_recycler.adapter = adapter
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
        reserveType = requireArguments().getString(StaticVariables.RESERVE_TYPE).toString()
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
            requireActivity().onBackPressed()
        }
    }

    // paging
    override fun onStart() {
        super.onStart()
        viewModel.refreshAllList()
    }
    private fun initAdapter(){
        adapter = RecyclerAdapterPaging(
                object : RecyclerAdapterPaging.OnClickListener{
                    override fun onRefresh() {
                    }

                    override fun whenListIsUpdated(size: Int, networkState: NetworkState?) {
                    }

                }
        )
        adapter.onClickHandler = object : OnCLickHandler<Any> {
            override fun onClickItem(element: Any) {}
            override fun onClick(view: View) {}
            override fun onClickView(view: View, element: Any) {}
        }
    }
    private fun configureObservables() {
        viewModel.networkState?.observe(viewLifecycleOwner, Observer { adapter.updateNetworkState(it) })
        viewModel.users.observe(viewLifecycleOwner, Observer { adapter.submitList(it as PagedList<Any>) })
    }
    private fun configViewModel(bundle:String){
        viewModel.fetchQuery(bundle)
    }
    private fun updateUIWhenLoading(size:Int , networkState: NetworkState?){
//        fra_show_items_progress.visibility = if(size == 0 && networkState == NetworkState.RUNNING) View.VISIBLE else View.GONE // todo loading
    }
    private fun updateUIWhenEmptyList(size:Int , networkState: NetworkState?){
        // todo empty list
        /*
        fra_show_items_img_status.visibility = View.GONE
        fra_show_items_status_txt.visibility = View.GONE
        fra_show_items_retry.visibility = View.GONE
        if(size == 0){
            when(networkState){
                NetworkState.SUCCESS ->{
                    Glide.with(this).load(R.drawable.not_found).into(fra_show_items_img_status)
                    fra_show_items_status_txt.text = getString(R.string.items_not_found)
                    fra_show_items_img_status.visibility = View.VISIBLE
                    fra_show_items_status_txt.visibility = View.VISIBLE
                    fra_show_items_retry.visibility = View.GONE
                }
                NetworkState.FAILED ->{
                    Glide.with(this).load(R.drawable.no_connection).into(fra_show_items_img_status)
                    fra_show_items_status_txt.text = getString(R.string.internet_error)
                    fra_show_items_img_status.visibility = View.VISIBLE
                    fra_show_items_status_txt.visibility = View.VISIBLE
                    fra_show_items_retry.visibility = View.VISIBLE
                }
            }
        }*/
    }

}