package com.imassage.data.model

import com.google.gson.annotations.SerializedName

data class MainPage(
        @SerializedName("massages")
        val massages: List<Massage> ,
        @SerializedName("boarders")
        val boarders: List<Boarder> ,
        @SerializedName("aboutUs")
        val aboutUs: List<AboutUs>
)