package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName
import com.imassage.data.model.Question

data class QuestionResponse(
        @SerializedName("datas")
        val datas: List<Question>
)