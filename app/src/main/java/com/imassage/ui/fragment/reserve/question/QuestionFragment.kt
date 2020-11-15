package com.imassage.ui.fragment.reserve.question

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
import com.imassage.data.model.Answer
import com.imassage.data.remote.model.AnswerRequest
import com.imassage.databinding.FragmentQuestionBinding
import com.imassage.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class QuestionFragment : ScopedFragment() , KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: QuestionViewModelFactory by instance()

    private lateinit var viewModel: QuestionViewModel
    private lateinit var binding: FragmentQuestionBinding
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = FragmentQuestionBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(QuestionViewModel::class.java)
        bindUI()
    }
    private fun bindUI() = launch{
        val answerResponse = AnswerRequest(arrayListOf(
                Answer("1" , "20")
        ))
        when(val callback = viewModel.answer(answerResponse)){
            is NetworkResponse.Success ->{
                Log.e("Log__" , "the response is ${callback.body.formId}")
            }
        }
    }

}