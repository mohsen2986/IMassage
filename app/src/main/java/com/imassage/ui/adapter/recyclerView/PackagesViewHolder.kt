package com.imassage.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.imassage.databinding.RowPackageTestBinding
import com.imassage.ui.utils.OnCLickHandler
import com.imassage.data.model.Package

class PackagesViewHolder(
        private val itemBinding: RowPackageTestBinding
): RecyclerView.ViewHolder(itemBinding.root){
    fun <T> bind(item: Package, onClickHandler: OnCLickHandler<T>?){
        itemBinding.packages = item
        onClickHandler?.let{
        }
    }
}