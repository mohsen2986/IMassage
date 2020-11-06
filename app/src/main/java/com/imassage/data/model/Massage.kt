package com.imassage.data.model

import com.google.gson.annotations.SerializedName

data class Massage(
        @SerializedName("name")
        val name: String ,
        @SerializedName("cost")
        val cost: String ,
        @SerializedName("length")
        val length: String ,
        @SerializedName("image")
        val image: String
)