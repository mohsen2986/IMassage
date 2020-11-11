package com.imassage.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.imassage.data.model.Massage
import com.imassage.databinding.RowMassageTestBinding
import com.imassage.ui.utils.OnCLickHandler

class MassagesViewHolder(
        private val itemBinding: RowMassageTestBinding
): RecyclerView.ViewHolder(itemBinding.root){
    fun <T> bind(item: Massage, onClickHandler: OnCLickHandler<T>?){
        itemBinding.massage = item
        onClickHandler?.let{
        }
    }
}