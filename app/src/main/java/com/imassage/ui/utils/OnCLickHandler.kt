package com.imassage.ui.utils

import android.view.View

interface OnCLickHandler<T>{
//    fun onClick(element: T)
    fun onClick(view: View)
    fun onClickView(view: View , element: T)
}