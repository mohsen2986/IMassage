package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName

data class SuccessResponse(
        @SerializedName("status")
        val status: String ,
        @SerializedName("code")
        val code: String
)