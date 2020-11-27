package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName
import com.imassage.data.model.Order

data class OrderHistoryResponse(
        @SerializedName("datas")
        val datas: List<Order>
)