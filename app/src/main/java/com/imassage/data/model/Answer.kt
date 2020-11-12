package com.imassage.data.model

import com.google.gson.annotations.SerializedName

class Answer(
        @SerializedName("question")
        val question: String ,
        @SerializedName("answer")
        val answer: String
)