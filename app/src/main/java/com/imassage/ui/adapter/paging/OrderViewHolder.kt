package com.imassage.ui.adapter.paging

import androidx.recyclerview.widget.RecyclerView
import com.imassage.data.model.Orders
import com.imassage.databinding.RowOrderBinding
import com.imassage.ui.utils.OnCLickHandler

class OrderViewHolder(
        private val itemBinding: RowOrderBinding
): RecyclerView.ViewHolder(itemBinding.root){

    fun bind(item: Orders, onClick: OnCLickHandler<Any>?){
        itemBinding.order = item
    }
}