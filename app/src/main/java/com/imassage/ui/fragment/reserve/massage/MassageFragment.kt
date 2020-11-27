package com.imassage.ui.fragment.reserve.massage

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.data.model.Massage
import com.imassage.databinding.FragmentMassageBinding
import com.imassage.ui.adapter.recyclerView.RecyclerAdapter
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.OnCLickHandler
import com.imassage.ui.utils.StaticVariables
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
        setEnterTransitions()
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(MassageViewModel::class.java)
        initAdapter()
        bindAdapter()
        UIActions()
        bindUI()
    }

    private fun bindUI() = launch{
        viewModel.mainPageData().await().let {
            massagesAdapter.datas = it.massages

//            navController.navigate(R.id.action_massageFragment_to_packageFragment);
        }
//        when(val data = viewModel.massages()){
//            is NetworkResponse.Success ->{
//                massagesAdapter.datas = data.body.datas
//                delay(2_000)
//                navController.navigate(R.id.action_massageFragment_to_packageFragment);
//            }
//        }
    }
    private fun initAdapter(){
        massagesAdapter = RecyclerAdapter()
        massagesAdapter.isGrid = true
        massagesAdapter.onClickHandler = object : OnCLickHandler<Massage> {
            override fun onClickItem(element: Massage) {
                viewModel.massageId(element.id)
                viewModel.massage(element)
                navController.navigate(R.id.action_massageFragment_to_packageFragment , bundleOf(StaticVariables.MASSAGE_ID to element.id))

            }
            override fun onClick(view: View) {}
            override fun onClickView(view: View, element: Massage) {}

        }
    }
    private fun bindAdapter(){
        fra_massage_recycler.apply {
            setExitTransitions()
            adapter = massagesAdapter
        }
    }
    private fun UIActions(){
        fra_massage_back.setOnClickListener{
            activity!!.onBackPressed()
        }
    }

    private fun setEnterTransitions(){
        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.fra_main_page_reserve)
            endView = binding.fraMassageContainer
            duration = 500L
            scrimColor = Color.TRANSPARENT
            containerColor = R.drawable.back_test
            startContainerColor = ContextCompat.getColor(requireContext(),R.color.avatar_color_7)
            endContainerColor = R.drawable.back_test
        }
        returnTransition = Slide().apply {
            duration = 500L
            addTarget(R.id.fra_massage_container)
        }
    }
    private fun setExitTransitions() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = 500L
        }

        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = 500L
        }
    }

}