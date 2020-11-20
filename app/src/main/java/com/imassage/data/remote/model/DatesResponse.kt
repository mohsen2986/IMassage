package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName

data class DatesResponse(
        @SerializedName("datas")
        val dates:List<String>
)