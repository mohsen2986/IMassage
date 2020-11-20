package com.imassage.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.imassage.data.model.ReserveTime
import com.imassage.databinding.RowReserveTimeBinding
import com.imassage.ui.utils.OnCLickHandler

class ReserveTimeViewHolder(
        private val itemBinding: RowReserveTimeBinding
): RecyclerView.ViewHolder(itemBinding.root){

    fun <T> bind(item: ReserveTime, onClickHandler: OnCLickHandler<T>?){
        itemBinding.reserveTime = item
        onClickHandler?.let{
            itemBinding.onCLickHandler = onClickHandler
        }
    }

}