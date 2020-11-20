package com.imassage.data.model

import com.google.gson.annotations.SerializedName
import com.imassage.data.remote.model.Packages

data class Order(
        @SerializedName("time")
        val time: String ,
        @SerializedName("reserved_time_date_id")
        val reservedTimeDateId: String ,
        @SerializedName("massage_id")
        val massageId: String ,
        @SerializedName("package_id")
        val packageId: String ,
        @SerializedName("transactions_id")
        val transactionId: String ,
        @SerializedName("gender")
        val gender: String ,
        @SerializedName("massage")
        val massage: Massage ,
        @SerializedName("packages")
        val packages: Packages ,
        @SerializedName("transactions")
        val transactions: Transactions ,
        @SerializedName("reserved_time_dates")
        val reservedTimeDates: DateTimes ,
        @SerializedName("times")
        val times: List<Time>
)