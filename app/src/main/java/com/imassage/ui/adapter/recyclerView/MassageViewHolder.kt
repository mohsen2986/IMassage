package com.imassage.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.imassage.data.model.Massage
import com.imassage.databinding.RowMassageTitleBinding
import com.imassage.ui.utils.OnCLickHandler

class MassageViewHolder(
        private val itemBinding : RowMassageTitleBinding
): RecyclerView.ViewHolder(itemBinding.root){
    fun <T> bind(item: Massage, onClickHandler: OnCLickHandler<T>?){
        itemBinding.massage = item
        onClickHandler?.let {
            itemBinding.onClick = it
        }
    }
}