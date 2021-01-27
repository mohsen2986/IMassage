package com.imassage.ui.fragment.mainPage

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialElevationScale
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
import com.imassage.ui.utils.OnCLickHandler
import com.imassage.ui.utils.StaticVariables
import com.imassage.ui.utils.resetApplication
import kotlinx.android.synthetic.main.fragment_main_page.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
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
        fra_main_page_massage_text.movementMethod = ScrollingMovementMethod()
        uiActions()
        initAdapter()
        bindAdapter()
        bindUI()
        initOnClickListeners()
        initDrawer()
    }
    private fun bindUI() = launch {
        viewModel.mainPageData().await().let {
            imageSliderAdapter.datas = it.boarders
            massageAdapter.datas = it.massages

            if(it.aboutUs.isNotEmpty())
                binding.aboutUs = it.aboutUs[0]

            if(it.massages.isNotEmpty())
                binding.massage = it.massages[0]
        }
        // get packages
        viewModel.packageData().await()
        val account = viewModel.getAccountData().await()
        binding.account = account

        if(account.gender == "true"){
            viewModel.setMaleGender()
        }else{
            viewModel.setFemaleGender()
        }
    }
    private fun uiActions(){
        setFragmentResultListener("request") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getString("bundleKey")
            // Do something with the result
            if(result == StaticVariables.REFRESH){
                updateAccount()
            }
        }
    }
    private fun updateAccount() = launch{
        when (val callback = viewModel.accountInformation()){
            is NetworkResponse.Success ->{
                binding.account = callback.body
                if(callback.body.gender == "true"){
                    viewModel.setMaleGender()
                }else{
                    viewModel.setFemaleGender()
                }
            }
        }
    }
    private fun initOnClickListeners(){
        fra_main_page_reserve.setOnClickListener{
            setExitTransitions()
            navController.navigate(R.id.action_mainPageFragment_to_massageFragment)
        }
    }
    private fun initAdapter(){
        imageSliderAdapter = ImageSliderAdapter()
        massageAdapter = RecyclerAdapter()
        massageAdapter.onClickHandler = object: OnCLickHandler<Massage>{
            override fun onClick(view: View) {}

            override fun onClickView(view: View, element: Massage) {}

            override fun onClickItem(element: Massage) {
                binding.massage = element
            }

        }
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
        drawer_account.setOnClickListener{
            navController.navigate(R.id.action_mainPageFragment_to_accountFragment)
        }
        drawer_history.setOnClickListener{
            navController.navigate(R.id.action_mainPageFragment_to_reserve_view ,
                    bundleOf(StaticVariables.RESERVE_TYPE to StaticVariables.HISTORY)
            )
        }
        drawer_reserved_time.setOnClickListener{
            navController.navigate(R.id.action_mainPageFragment_to_reserve_view ,
                    bundleOf( StaticVariables.RESERVE_TYPE to StaticVariables.RESERVE_TIME)
            )
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
                getDate()
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
            StaticVariables.SIGN_UP_FORM ->{
                getDate()
            }
        }
    }
    private fun getDate() = launch {
        when(val callback = viewModel.mainPage()){
            is NetworkResponse.Success ->{
                val it = callback.body.datas
                imageSliderAdapter.datas = it.boarders
                massageAdapter.datas = it.massages

                if(it.aboutUs.isNotEmpty())
                    binding.aboutUs = it.aboutUs[0]

                if(it.massages.isNotEmpty())
                    binding.massage = it.massages[0]

                // get packages
                viewModel.packageData().await()
            }
        }
        val account = viewModel.getAccountData().await()
        binding.account = account
        if(account.gender == "true"){
            viewModel.setMaleGender()
        }else{
            viewModel.setFemaleGender()
        }
    }

    private fun setExitTransitions() {
        exitTransition = MaterialElevationScale(false).apply {
            duration = 500L
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 500L
        }
    }

}