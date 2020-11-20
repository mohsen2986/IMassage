package com.imassage.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.imassage.data.model.Massage
import com.imassage.data.model.Order
import com.imassage.databinding.RowReservationViewBinding
import com.imassage.ui.utils.OnCLickHandler

class ReserveViewHolder(
        private val itemBinding: RowReservationViewBinding
): RecyclerView.ViewHolder(itemBinding.root){
    fun <T> bind(item: Order, onClickHandler: OnCLickHandler<T>?){
        itemBinding.order = item
        onClickHandler?.let {
        }
    }
}