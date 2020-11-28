package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName

class MetaData(
        @SerializedName("pagination")
        val pagination: Pagination
)