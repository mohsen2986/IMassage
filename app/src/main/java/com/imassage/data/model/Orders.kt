package com.imassage.data.model
import com.google.gson.annotations.SerializedName
import java.lang.Package


data class Orders(
        @SerializedName("massage")
        val massage: Massage,
        @SerializedName("package")
        val package_: String,
        @SerializedName("reserve_time")
        val reservedTimeDateId: DateTimes,
        @SerializedName("transaction")
        val transactions: Transactions,
        @SerializedName("times")
        val times: List<Times>
)