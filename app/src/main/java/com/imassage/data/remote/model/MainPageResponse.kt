package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName
import com.imassage.data.model.MainPage

data class MainPageResponse(
        @SerializedName("datas")
        val datas: MainPage
)