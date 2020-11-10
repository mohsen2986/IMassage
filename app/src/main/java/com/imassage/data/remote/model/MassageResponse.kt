package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName
import com.imassage.data.model.Massage

data class MassageResponse(
        @SerializedName("datas")
        val datas: List<Massage>
)