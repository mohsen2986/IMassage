package com.imassage.ui.adapter.questionRecyclerView

import android.R
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.imassage.data.model.Question
import com.imassage.databinding.RowAnswerQuestionsBinding
import com.imassage.generated.callback.OnClickListener
import com.imassage.ui.utils.OnCLickHandler
import com.wdullaer.materialdatetimepicker.JalaliCalendar
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*


class QuestionViewHolder(
        private val itemBinding:RowAnswerQuestionsBinding,
        private val supportFragmentManager: FragmentManager ,
        answers: (Int, String) -> Unit
): RecyclerView.ViewHolder(itemBinding.root){
    init {
        itemBinding.rowAnswerQuestionAnswer.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                itemBinding.rowAnswerQuestionAnswer.tag?.let {
                    answers((itemBinding.rowAnswerQuestionAnswer.tag as Int), p0.toString())
                }
            }
          })
        }
    fun <T> bind(item: Question, onClickHandler: OnCLickHandler<T>? , position: Int){
        itemBinding.question = item
        itemBinding.rowAnswerQuestionAnswer.tag = position
//        if (position == 0) {
//            itemBinding.onClick = object : OnCLickHandler<Nothing> {
//                override fun onClickItem(element: Nothing) {}
//                override fun onClickView(view: View, element: Nothing) {}
//                override fun onClick(view: View) {
//                    val calendarType: DatePickerDialog.Type = DatePickerDialog.Type.JALALI
//                    val now: JalaliCalendar = JalaliCalendar.getInstance()
//                    val dp = DatePickerDialog.newInstance(
//                            calendarType,
//                            { view, year, monthOfYear, dayOfMonth ->
//                            },
//                            now.get(Calendar.YEAR),
//                            now.get(Calendar.MONTH),
//                            now.get(Calendar.DAY_OF_MONTH)
//                    )
//                    dp.onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                        "$year-$monthOfYear-$dayOfMonth".let {
//                            itemBinding.rowAnswerQuestionAnswer.setText(it)
//                        }
//                    }
//                    dp.show(supportFragmentManager, "DatePickerDialog")
//                }
//            }
//        }
        onClickHandler?.let{
        }
    }
}
