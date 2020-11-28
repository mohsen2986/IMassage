package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName
import com.imassage.data.model.Orders

data class OrderResponse(
        @SerializedName("data")
        val data: List<Orders>,
        @SerializedName("meta")
        val metadata: MetaData
)