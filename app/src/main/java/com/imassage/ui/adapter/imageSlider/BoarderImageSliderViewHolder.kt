package com.imassage.ui.adapter.imageSlider

import com.imassage.data.model.Boarder
import com.imassage.databinding.RowBoarderImageSliderBinding
import com.imassage.ui.utils.OnCLickHandler
import com.smarteist.autoimageslider.SliderViewAdapter

class BoarderImageSliderViewHolder(
    private val itemDataBinding: RowBoarderImageSliderBinding
): SliderViewAdapter.ViewHolder(itemDataBinding.root){
    fun <T> bind(item: Boarder , onClickHandler: OnCLickHandler<T>?){
        itemDataBinding.boarder = item
    }
}