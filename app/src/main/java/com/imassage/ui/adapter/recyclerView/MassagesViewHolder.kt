package com.imassage.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.imassage.data.model.Massage
import com.imassage.databinding.RowMassageKindsBinding
import com.imassage.ui.utils.OnCLickHandler

class MassagesViewHolder(
        private val itemBinding: RowMassageKindsBinding
): RecyclerView.ViewHolder(itemBinding.root){
    fun <T> bind(item: Massage, onClickHandler: OnCLickHandler<T>?){
        itemBinding.massage = item
        onClickHandler?.let{
        }
    }
}