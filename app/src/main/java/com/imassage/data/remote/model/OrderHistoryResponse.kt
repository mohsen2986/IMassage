package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName

data class OrderHistoryResponse(
        @SerializedName("datas")
        val datas: List<Data>
)