package com.imassage.ui.fragment.reserve.reserveTime

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.data.model.DateTimes
import com.imassage.data.model.ReserveTime
import com.imassage.databinding.FragmentReservedTimeBinding
import com.imassage.ui.adapter.recyclerView.RecyclerAdapter
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.OnCLickHandler
import com.wdullaer.materialdatetimepicker.JalaliCalendar
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_massage.*
import kotlinx.android.synthetic.main.fragment_reserved_time.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.*

class ReservedTimeFragment : ScopedFragment() , KodeinAware{
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ReservedTimeViewModelFactory by instance()

    private lateinit var viewModel: ReservedTimeViewModel
    private lateinit var binding: FragmentReservedTimeBinding
    private lateinit var navController: NavController
    // -- FOR DATA
    private lateinit var timesAdapter: RecyclerAdapter<ReserveTime>

//    var dpd: DatePickerDialog? = null
    private var calendarType: DatePickerDialog.Type = DatePickerDialog.Type.JALALI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservedTimeBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(ReservedTimeViewModel::class.java)
        initAdapter()
        bindAdapter()
        UIActions()
        bindUI()
    }
    private fun bindUI() = launch {
        val callback = viewModel.availableDates().await()
        initOnClickListeners(callback)
    }
    private fun getAvailableDates(date: String) = launch {
        val convertedDate = date.replace('/' , '-')
        viewModel.setDate(convertedDate)
            when(val times = viewModel.availableDateTime(convertedDate)){
                is NetworkResponse.Success ->{
                    timesAdapter.datas = converter(times.body)
                }
            }
    }
    private fun initAdapter(){
        timesAdapter = RecyclerAdapter()
        timesAdapter.onClickHandler = object: OnCLickHandler<ReserveTime>{
            override fun onClickItem(element: ReserveTime) {
                viewModel.setTime(element.clock)
                sendAndCheckSelectedTime()
            }

            override fun onClick(view: View) {}
            override fun onClickView(view: View, element: ReserveTime) {}
        }
    }
    private fun sendAndCheckSelectedTime() = launch {
        if(viewModel.checkReserveTime()){
            viewModel.reserveTime()
            navController.navigate(R.id.action_reservedTimeFragment_to_receiptFragment)
        }else{
            Toast.makeText(context , "زمان دیگری را انتخاب کنید." , Toast.LENGTH_SHORT).show()
        }
    }
    private fun bindAdapter(){
        fra_reserved_time_recycler.apply {
            adapter = timesAdapter
        }
    }
    private fun converter(dateTimes: DateTimes):List<ReserveTime>{
        val clock = "ساعت"
        return dateTimes.let {
            listOf(
                ReserveTime("$clock 8",  "h8",  it.genderClock8 , it.clock8 != "1"&& it.genderClock8   == viewModel.gender().toString()),
                ReserveTime("$clock 9",  "h9",  it.genderClock9 , it.clock9 != "1"&& it.genderClock9   == viewModel.gender().toString()),
                ReserveTime("$clock 10", "h10", it.genderClock10, it.clock10 != "1"&& it.genderClock10 == viewModel.gender().toString()),
                ReserveTime("$clock 11", "h11", it.genderClock11, it.clock11 != "1"&& it.genderClock11 == viewModel.gender().toString()),
                ReserveTime("$clock 12", "h12", it.genderClock12, it.clock12 != "1"&& it.genderClock12 == viewModel.gender().toString()),
                ReserveTime("$clock 13", "h13", it.genderClock13, it.clock13 != "1"&& it.genderClock13 == viewModel.gender().toString()),
                ReserveTime("$clock 14", "h14", it.genderClock14, it.clock14 != "1"&& it.genderClock14 == viewModel.gender().toString()),
                ReserveTime("$clock 15", "h15", it.genderClock15, it.clock15 != "1"&& it.genderClock15 == viewModel.gender().toString()),
                ReserveTime("$clock 16", "h16", it.genderClock16, it.clock16 != "1"&& it.genderClock16 == viewModel.gender().toString()),
                ReserveTime("$clock 17", "h17", it.genderClock17, it.clock17 != "1"&& it.genderClock17 == viewModel.gender().toString()),
                ReserveTime("$clock 18", "h18", it.genderClock18, it.clock18 != "1"&& it.genderClock18 == viewModel.gender().toString()),
                ReserveTime("$clock 19", "h19", it.genderClock19, it.clock19 != "1"&& it.genderClock19 == viewModel.gender().toString()),
                ReserveTime("$clock 20", "h20", it.genderClock20, it.clock20 != "1"&& it.genderClock20 == viewModel.gender().toString()),
                ReserveTime("$clock 21", "h21", it.genderClock21, it.clock21 != "1"&& it.genderClock21 == viewModel.gender().toString()),
                ReserveTime("$clock 22", "h22", it.genderClock22, it.clock22 != "1"&& it.genderClock22 == viewModel.gender().toString())
            )
        }
    }
    private fun initOnClickListeners(availableDates: List<String>){
        val selectableDays:MutableList<Calendar> = mutableListOf(
                availableDates[0].split('-').let {
                    JalaliCalendar.getInstance().apply {
                        set(it[0].toInt() , it[1].toInt() , it[2].toInt())
                    }
                }
        )
        availableDates.forEach { day ->
            selectableDays.add(day.split('-').let {
                Calendar.getInstance().apply {
                    set(it[0].toInt() , it[1].toInt() , it[2].toInt())
                }
            })
        }

        fra_reserved_time_date.setOnClickListener{
            val now: JalaliCalendar = JalaliCalendar.getInstance()
                val dp = DatePickerDialog.newInstance(
                        calendarType,
                        { view, year, monthOfYear, dayOfMonth ->
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                )
            dp.selectableDays = selectableDays.toTypedArray()
            dp.onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                "$year/$monthOfYear/$dayOfMonth".let {
                    getAvailableDates(it)
                    binding.date = it
                }
                getAvailableDates("$year/$monthOfYear/$dayOfMonth")
            }

            dp.show(requireActivity().supportFragmentManager, "DatePickerDialog")
        }
      }
        private fun UIActions(){
            fra_reserved_time_back.setOnClickListener{
                requireActivity().onBackPressed()
            }
    }
    }



