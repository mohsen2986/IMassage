package com.imassage.ui.adapter.recyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imassage.R
import com.imassage.data.model.Massage
import com.imassage.data.model.Package
import com.imassage.databinding.RowMassageKindsBinding
import com.imassage.databinding.RowMassagePackageBinding
import com.imassage.databinding.RowMassageTitleBinding
import com.imassage.ui.utils.OnCLickHandler

class RecyclerAdapter<T>(
):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var isGrid = false
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
            R.layout.row_massage_kinds ->
                MassagesViewHolder(
                        RowMassageKindsBinding.inflate(layoutInflater, parent, false)
                )
            R.layout.row_massage_package ->
                PackagesViewHolder(
                        RowMassagePackageBinding.inflate(layoutInflater , parent , false)
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
        }
    }

    override fun getItemViewType(position: Int): Int =
            when(datas[0]){
                is Massage -> if (isGrid) R.layout.row_massage_kinds else R.layout.row_massage_title
                is Package -> R.layout.row_massage_package
                else -> throw IllegalStateException("the type is invalid!")
            }
}