package com.imassage.data.model

import com.google.gson.annotations.SerializedName

data class Account(
        @SerializedName("phone")
        val phone: String ,
        @SerializedName("name")
        val name: String ,
        @SerializedName("family")
        val family: String ,
        @SerializedName("photo")
        val photo: String ,
        @SerializedName("gender")
        val gender: String ,
        @SerializedName("address")
        val address: String
)