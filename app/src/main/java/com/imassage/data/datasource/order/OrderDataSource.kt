package com.imassage.data.datasource.order

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.haroldadmin.cnradapter.NetworkResponse
import com.imassage.data.remote.model.NetworkState
import com.imassage.data.repository.OrderRepository
import com.imassage.ui.utils.StaticVariables
import kotlinx.coroutines.*

class OrderDataSource<T>(
        private val repository: OrderRepository,
        private val query:String,
        private val scope: CoroutineScope
) : PageKeyedDataSource<Int, T>() {
    // FOR DATA---
    private val supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? = null // Keep reference of the last query (to be able to retry it if necessary)
    private var pages :Int ?= null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        retryQuery = { loadInitial(params, callback) }
        loadInitial {
            callback.onResult(it , null , if(pages == 1) null else 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val page = params.key
        retryQuery = {loadAfter(params , callback)}
        executeQuery(page){
            callback.onResult( it , if((page+1) == pages ) null else page+1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {}
    // UTILS--
    private fun executeQuery(page:Int , callBack:(List<T>) -> Unit ){
        networkState.postValue(NetworkState.RUNNING)
        scope.launch (getJobErrorHandler() + supervisorJob){
//            delay(200)
            val request = if(query == StaticVariables.HISTORY) repository.ordersHistory_(page) else repository.orders(page)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            when(request){
                is NetworkResponse.Success ->{
                    callBack((request.body.data) as List<T>)
                    Log.e("Log__" , "some issue ${request.body.data}")
                    }
                else -> Log.e("Log__" , "some issue")
                }
            }
        }

    private fun loadInitial(callBack:(List<T>) -> Unit ){
        scope.launch (getJobErrorHandler() + supervisorJob){
            val callback_ = if(query == StaticVariables.HISTORY) repository.ordersHistory_(0) else repository.orders(0)
            when(callback_){
                is NetworkResponse.Success -> pages = callback_.body.metadata.pagination.totalPages
                else -> Log.e("Log__" , "some issue")
            }
//            pages = repository.users(query).
            executeQuery(0) {
                callBack(it)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(OrderDataSource::class.java.simpleName, "An error happened: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }

    // PUBLIC API ---

    fun getNetworkState(): LiveData<NetworkState> =
        networkState

    fun refresh() =
        this.invalidate()


}