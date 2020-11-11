package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName
import com.imassage.data.model.Package

data class PackageResponse(
        @SerializedName("datas")
        val datas:List<Package>
)
//massage_id