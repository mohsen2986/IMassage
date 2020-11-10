package com.imassage.ui.fragment.mainPage

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.data.model.Boarder
import com.imassage.data.model.Massage
import com.imassage.data.remote.api.AuthApiInterface
import com.imassage.databinding.FragmentMainPageBinding
import com.imassage.ui.adapter.imageSlider.ImageSliderAdapter
import com.imassage.ui.adapter.recyclerView.RecyclerAdapter
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.StaticVariables
import com.imassage.ui.utils.resetApplication
import kotlinx.android.synthetic.main.fragment_main_page.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class MainPageFragment : ScopedFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: MainPageViewModelFactory by instance()

    private lateinit var viewModel: MainPageViewModel
    private lateinit var binding: FragmentMainPageBinding
    private lateinit var navController: NavController
    // -- FOR DATA
    private lateinit var imageSliderAdapter: ImageSliderAdapter<Boarder>
    private lateinit var massageAdapter: RecyclerAdapter<Massage>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sourceFragment()
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(MainPageViewModel::class.java)
        initAdapter()
        bindAdapter()
        bindUI()
        initDrawer()
    }
    private fun bindUI() = launch {
        when(val data = viewModel.mainPage()){
            is NetworkResponse.Success ->{
                imageSliderAdapter.datas = data.body.datas.boarders
                massageAdapter.datas = data.body.datas.massages
                binding.aboutUs = data.body.datas.aboutUs[0]

                Log.e("Log__" , "the data is ${data.body.datas.massages}")
            }
            is NetworkResponse.NetworkError -> {
                Log.e("Log__" , "the data network error!")
            }

            is NetworkResponse.UnknownError -> {
                Log.e("Log__" , "the data unknown error")
            }
            is NetworkResponse.ServerError ->{
                Log.e("Log__" , "the data server")
            }
        }
    }
    private fun initAdapter(){
        imageSliderAdapter = ImageSliderAdapter()
        massageAdapter = RecyclerAdapter()
    }
    private fun bindAdapter(){
        fra_main_page_slider.apply {
            sliderAdapter = imageSliderAdapter
        }
        fra_main_page_recycler_massage_titles.apply{
            adapter = massageAdapter
        }
    }
    private fun initDrawer(){
        drawer_logOut.setOnClickListener{
            logOut()
        }
    }
    private fun logOut() = launch{
        viewModel.logOut()
        resetApplication(activity)
    }
    // get source Fragment
    private fun sourceFragment(){
        when(arguments?.getString(StaticVariables.SOURCE_FRAGMENT)){
            StaticVariables.SPLASH_FRAGMENT -> {
                enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z,true).apply {
                    duration = 500L
                }
            }
            StaticVariables.VERIFICATION_CODE_FRAGMENT -> {
                enterTransition = MaterialContainerTransform().apply {
                    startView = requireActivity().findViewById(R.id.fra_phone_verification_next)
                    endView = binding.fraMainPageContainer
                    duration = 500L
                    scrimColor = Color.TRANSPARENT
                    containerColor = R.drawable.back_test
                    startContainerColor = ContextCompat.getColor(requireContext(),R.color.avatar_color_7)
                    endContainerColor = R.drawable.back_test
                }
                returnTransition = Slide().apply {
                    duration = 500L
                    addTarget(R.id.fra_main_page_container)
                }
            }
        }
    }

}