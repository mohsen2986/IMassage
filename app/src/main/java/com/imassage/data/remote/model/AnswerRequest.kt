package com.imassage.data.remote.model

import com.google.gson.annotations.SerializedName
import com.imassage.data.model.Answer

data class AnswerRequest(
        @SerializedName("answers")
        val answers:List<Answer>
)