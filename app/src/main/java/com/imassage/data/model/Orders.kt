package com.imassage.data.model
import com.google.gson.annotations.SerializedName


data class Orders(
        @SerializedName("massage")
        val massage: Massage,
        @SerializedName("package")
        val package_: Package,
        @SerializedName("reserve_time")
        val reservedTimeDateId: DateTimes,
        @SerializedName("transaction")
        val transactions: Transactions,
        @SerializedName("times")
        val times: List<Times>
)