package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName

data class Status(
        @SerializedName("status")
        val status: String
)