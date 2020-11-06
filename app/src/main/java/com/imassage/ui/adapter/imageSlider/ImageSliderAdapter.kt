package com.imassage.ui.adapter.imageSlider

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.imassage.R
import com.imassage.data.model.Boarder
import com.imassage.databinding.RowBoarderImageSliderBinding
import com.imassage.ui.utils.OnCLickHandler
import com.smarteist.autoimageslider.SliderViewAdapter
import java.lang.IllegalStateException

class ImageSliderAdapter<T>()
    :SliderViewAdapter<SliderViewAdapter.ViewHolder>(){

    var onClickHandler: OnCLickHandler<T> ?= null
    var datas: List<T> = listOf()
    set(value){
        field = value
        Log.e("Log__" , "size ${value.size}")
        notifyDataSetChanged()
    }

    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        layoutInflater = LayoutInflater.from(parent?.context)
        return when(getType()){
            R.layout.row_boarder_image_slider ->
                BoarderImageSliderViewHolder(
                        RowBoarderImageSliderBinding.inflate(layoutInflater, parent, false)
                )
            else -> throw IllegalStateException("the type is invalid!!")
        }
    }

    override fun getCount(): Int = datas.size

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        when(getType()){
            R.layout.row_boarder_image_slider ->
                (viewHolder as BoarderImageSliderViewHolder).bind(datas[position] as Boarder , onClickHandler = onClickHandler)
        }
    }
    private fun getType(): Int =
            when(datas[0]){
                is Boarder -> R.layout.row_boarder_image_slider
                else  -> throw IllegalStateException("the type is invalid!!")
            }

}