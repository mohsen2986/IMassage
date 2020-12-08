package com.imassage.ui.adapter.questionRecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.imassage.R
import com.imassage.data.model.Answer
import com.imassage.data.model.Question
import com.imassage.databinding.RowAnswerQuestionsBinding
import com.imassage.ui.utils.OnCLickHandler

class QuestionRecyclerView<T>(
        private val supportFragmentManager: FragmentManager
):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var answers: MutableList<Answer> = ArrayList()

    var onClickHandler: OnCLickHandler<T> ?= null
    var datas: List<T> = listOf()
    set(value){
        field = value
        value.forEach {
            answers.add(Answer( ((it) as Question).questionId , ""))
        }
        notifyDataSetChanged()
    }
    private lateinit var layoutInflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            R.layout.row_answer_questions ->
                QuestionViewHolder(
                        RowAnswerQuestionsBinding.inflate(layoutInflater , parent ,false ) , supportFragmentManager
                ) { position, answer ->
                    answers[position].answer = answer
                }
            else -> throw IllegalStateException("the type is invalid!!")
        }
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is QuestionViewHolder ->
                holder.bind(datas[position] as Question , onClickHandler = onClickHandler , position = position)
        }
    }

    override fun getItemViewType(position: Int): Int =
            when(datas[0]){
                is Question -> R.layout.row_answer_questions
                else -> throw IllegalStateException("the type is invalid!")
            }

    fun allQuestionAnswered() =
            !answers.any { answer -> answer.answer == "" }
}