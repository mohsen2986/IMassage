package com.imassage.ui.adapter.recyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imassage.R
import com.imassage.data.model.Boarder
import com.imassage.data.model.Massage
import com.imassage.databinding.RowMassageTitleBinding
import com.imassage.ui.utils.OnCLickHandler
import javax.annotation.meta.When

class RecyclerAdapter<T>(
):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var onClickHandler: OnCLickHandler<T> ?= null
    var datas: List<T> = listOf()
    set(value){
        field = value
        Log.e("Log__" , "set data is called")
        notifyDataSetChanged()
    }
    private lateinit var layoutInflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            R.layout.row_massage_title ->
                MassageViewHolder(
                        RowMassageTitleBinding.inflate(layoutInflater , parent , false)
                )
            else -> throw IllegalStateException("the type is invalid!!")
        }
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MassageViewHolder ->
                holder.bind(datas[position] as Massage , onClickHandler = onClickHandler)
        }
    }

    override fun getItemViewType(position: Int): Int =
            when(datas[0]){
                is Massage -> R.layout.row_massage_title
                else -> throw IllegalStateException("the type is invalid!")
            }
}