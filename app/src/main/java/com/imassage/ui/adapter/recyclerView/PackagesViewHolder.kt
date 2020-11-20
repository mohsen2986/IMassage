package com.imassage.ui.adapter.recyclerView

import androidx.recyclerview.widget.RecyclerView
import com.imassage.ui.utils.OnCLickHandler
import com.imassage.data.model.Package
import com.imassage.databinding.RowMassagePackageBinding

class PackagesViewHolder(
        private val itemBinding: RowMassagePackageBinding
): RecyclerView.ViewHolder(itemBinding.root){
    fun <T> bind(item: Package, onClickHandler: OnCLickHandler<T>?){
        itemBinding.packages = item
        onClickHandler?.let{
            itemBinding.onClickHandler = onClickHandler
        }
    }
}