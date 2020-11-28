package com.imassage.data.datasource.order

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.imassage.data.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope

class OrderDataSourceFactory<T>(
    private val repository: OrderRepository ,
    private var query: String ,
    private val scope: CoroutineScope
):DataSource.Factory<Int , T>(){
    val source = MutableLiveData<OrderDataSource<T>>()

    override fun create(): DataSource<Int, T> {
        val source = OrderDataSource<T>(repository , query, scope)
        this.source.postValue(source)
        return source
    }

    // --- PUBLIC API
    fun getSource() = source.value
    fun getQuery() = query

    fun updateQuery(query:String){
        this.query = query
        getSource()?.refresh()
    }

}