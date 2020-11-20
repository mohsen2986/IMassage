package com.imassage.ui.adapter.recyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imassage.R
import com.imassage.data.model.*
import com.imassage.databinding.*
import com.imassage.ui.adapter.questionRecyclerView.QuestionViewHolder
import com.imassage.ui.utils.OnCLickHandler

class RecyclerAdapter<T>(
):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var isGrid = false
    var onClickHandler: OnCLickHandler<T> ?= null
    var datas: List<T> = listOf()
    set(value){
        field = value
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
            R.layout.row_massage_kinds ->
                MassagesViewHolder(
                        RowMassageKindsBinding.inflate(layoutInflater, parent, false)
                )
            R.layout.row_massage_package ->
                PackagesViewHolder(
                        RowMassagePackageBinding.inflate(layoutInflater , parent , false)
                )
            R.layout.row_reserve_time ->
                ReserveTimeViewHolder(
                        RowReserveTimeBinding.inflate(layoutInflater , parent , false)
                )
            R.layout.row_reservation_view ->
                ReserveViewHolder(
                        RowReservationViewBinding.inflate(layoutInflater , parent , false)
                )

            else -> throw IllegalStateException("the type is invalid!!")
        }
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MassageViewHolder ->
                holder.bind(datas[position] as Massage , onClickHandler = onClickHandler)
            is MassagesViewHolder ->
                holder.bind(datas[position] as Massage , onClickHandler = onClickHandler)
            is PackagesViewHolder ->
                holder.bind(datas[position] as Package , onClickHandler = onClickHandler)
            is ReserveTimeViewHolder ->
                holder.bind(datas[position] as ReserveTime , onClickHandler = onClickHandler)
            is ReserveViewHolder ->
                holder.bind(datas[position] as Order , onClickHandler = onClickHandler)
        }
    }

    override fun getItemViewType(position: Int): Int =
            when(datas[0]){
                is Massage -> if (isGrid) R.layout.row_massage_kinds else R.layout.row_massage_title
                is Package -> R.layout.row_massage_package
                is Question -> R.layout.row_answer_questions
                is ReserveTime -> R.layout.row_reserve_time
                is Order -> R.layout.row_reservation_view
                else -> throw IllegalStateException("the type is invalid!")
            }
}