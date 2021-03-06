package com.imassage.data.model

import com.google.gson.annotations.SerializedName

data class Package(
        @SerializedName("name")
        val name: String ,
        @SerializedName("description")
        val description: String ,
        @SerializedName("image")
        val image: String ,
        @SerializedName("cost")
        val cost: Int ,
        @SerializedName("massage_id")
        val massageId: Massage ,
        @SerializedName("id")
        val packageId: String

)