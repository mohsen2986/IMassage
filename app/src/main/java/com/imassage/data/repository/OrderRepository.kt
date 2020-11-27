package com.imassage.data.repository

import com.haroldadmin.cnradapter.NetworkResponse
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
    var filledFormId = ""

    lateinit var massage: Massage
    lateinit var packages: Package

    val availableDates by lazyDeferred{
         when(val callback = apiInterface.availableDates()){
             is NetworkResponse.Success -> callback.body.dates
             else -> listOf()
         }
     }
    suspend fun availableDateTime( date: String ) =
            apiInterface.availableDateTime(date)

    suspend fun checkTime():Boolean =
        withContext(IO) {
            if (time != "" && date != "") {
                when (val callback = apiInterface.checkSelectedTime("$time", date , massageId, gender.toString())) {
                    is NetworkResponse.Success -> {
                        return@withContext true
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
    suspend fun orders() =
            apiInterface.orders()

    // set offer
    suspend fun offer(transactionId: String , offerCode: String) =
            apiInterface.setOffer(transactionId , offerCode)
    // send order
    suspend fun sendOrder() {
        GlobalScope.launch(IO) {
            when(val callback = apiInterface.order(filledFormId , time , date , massageId , packageId , transactionId , gender.toString() )){
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