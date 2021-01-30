package com.imassage.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry
import com.imassage.data.model.Massage
import com.imassage.data.model.Package
import com.imassage.data.remote.api.AuthApiInterface
import com.imassage.data.remote.model.DatesResponse
import com.imassage.ui.base.lazyDeferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderRepository(
        private val apiInterface: AuthApiInterface
){
    var gender = false
    var massageId = "1"
    var packageId = "1"
    var date  = ""
    var time = ""
    var transactionId = ""

    lateinit var massage: Massage
    lateinit var packages: Package

    val availableDates by lazyDeferred{
         when(val callback = executeWithRetry(times = 5) {apiInterface.availableDates()}){
             is NetworkResponse.Success -> callback.body.dates
             else -> null
         }
     }
    suspend fun availableDateTime( date: String ) =
            apiInterface.availableDateTime(date , packageId)

    suspend fun checkTime():Boolean =
        withContext(IO) {
            if (time != "" && date != "") {
                when (val callback = apiInterface.checkSelectedTime(date , time , packageId, gender.toString())) {
                    is NetworkResponse.Success -> {
                        return@withContext callback.body.code == "200"
                    }
                    is NetworkResponse.ServerError -> {
                        return@withContext false
                    }
                    else -> return@withContext false
                }
            }
            else{
                return@withContext false
        }
    }
    // transaction
    suspend fun transaction(offerCode: String?) =
        apiInterface.transaction(packageId , offerCode)
    // validate transaction
    suspend fun payTransaction(refId: String) =
            apiInterface.payTransaction(transactionId , refId)
    // orders history
    suspend fun ordersHistory() =
            apiInterface.ordersHistory()
    // get orders
    suspend fun orders(page: Int?) =
            apiInterface.orders(page)

    suspend fun ordersHistory_(page: Int?) =
            apiInterface.ordersHistory_(page)

    // set offer
    suspend fun offer(transactionId: String , offerCode: String) =
            apiInterface.setOffer(transactionId , offerCode , massageId)
    // send order
    suspend fun sendOrder() {
        GlobalScope.launch(IO) {
            when(val callback = apiInterface.order(time , date , massageId , packageId , transactionId , gender.toString() )){
                is NetworkResponse.Success ->{

                }
            }
        }
    }
    suspend fun sendReserveTime(){
        GlobalScope.launch(IO) {
            apiInterface.reserveTime(time , date , massageId , packageId , gender.toString())
        }
    }
}