package com.imassage.ui.fragment.reserve.question

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialSharedAxis
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.R
import com.imassage.data.model.Answer
import com.imassage.data.model.Question
import com.imassage.data.remote.model.AnswerRequest
import com.imassage.databinding.FragmentQuestionBinding
import com.imassage.ui.adapter.questionRecyclerView.QuestionRecyclerView
import com.imassage.ui.adapter.recyclerView.RecyclerAdapter
import com.imassage.ui.base.ScopedFragment
import com.imassage.ui.utils.OnCLickHandler
import com.imassage.ui.utils.StaticVariables
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.coroutines.delay
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

    // FOR DATA
    private lateinit var questionAdapter: QuestionRecyclerView<Question>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = FragmentQuestionBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEnterTransitions()
        navController = findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this , viewModelFactory).get(QuestionViewModel::class.java)
        initAdapters()
        bindAdapters()
        bindUI()
        sendConsulting()
        uiActions()
    }
    private fun bindUI() = launch{
        when(val callback = viewModel.questions()){
            is NetworkResponse.Success ->{
                questionAdapter.datas = callback.body.datas
            }
        }
        fra_question_submit.setOnClickListener{
            if(questionAdapter.allQuestionAnswered()){
                sendAnswers()
            }else{
                Toast.makeText(context , "همه سوالات را پر کنید." , Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun sendAnswers() = launch{
       val answerResponse = AnswerRequest(questionAdapter.answers)
        when(val callBack = viewModel.answer(answerResponse)){
            is NetworkResponse.Success -> {
                callBack.body?.let {
                    navController.navigate(R.id.action_questionFragment_to_receiptFragment)
                }
            }
        }
    }
    private fun uiActions(){
        binding.onClick = object :OnCLickHandler<Nothing>{
            override fun onClickItem(element: Nothing) {}
            override fun onClickView(view: View, element: Nothing) {}
            override fun onClick(view: View) {
                when(view){
                    fra_question_back -> requireActivity().onBackPressed()
                }
            }

        }
    }
    private fun initAdapters(){
        questionAdapter = QuestionRecyclerView(requireActivity().supportFragmentManager)
    }
    private fun bindAdapters(){
        fra_question_recycler.apply{
            adapter = questionAdapter
        }
    }


    private fun setEnterTransitions() {
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true).apply {
            duration = 500L
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false).apply {
            duration = 500L
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