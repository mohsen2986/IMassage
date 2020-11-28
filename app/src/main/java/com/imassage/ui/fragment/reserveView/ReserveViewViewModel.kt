package com.imassage.ui.fragment.reserveView

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.imassage.data.datasource.order.OrderDataSourceFactory
import com.imassage.data.model.Orders
import com.imassage.data.remote.model.NetworkState
import com.imassage.data.repository.OrderRepository
import com.imassage.ui.utils.StaticVariables
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class ReserveViewViewModel(
        private val orderRepository: OrderRepository
) : ViewModel() {
    suspend fun ordersHistory() =
            orderRepository.ordersHistory()

    suspend fun reserves() =
            orderRepository.orders()

    // paging
    var pagingType = StaticVariables.HISTORY
    // DATA
    protected val ioScope = CoroutineScope(Dispatchers.IO)

    private val itemDataSource = OrderDataSourceFactory<Orders>(repository = orderRepository , query = "", scope = ioScope)

    // OBSERVABLES
    val users = LivePagedListBuilder(itemDataSource ,pagedListConfig()).build()
    val networkState : LiveData<NetworkState>? =
            Transformations.switchMap(itemDataSource.source) { it.getNetworkState() }


    fun fetchQuery(query:String){
        if(itemDataSource.getQuery() == query) return
        itemDataSource.updateQuery(query = query)
    }
    fun getQuery() = itemDataSource.getQuery()
    // refresh all list after an issue
    fun refreshAllList() = itemDataSource.getSource()?.refresh()

    // UTILS
    private fun pagedListConfig() =
            PagedList.Config.Builder()
                    .setInitialLoadSizeHint(5)
                    .setEnablePlaceholders(false)
                    .setPageSize(5)
                    .build()

    override fun onCleared() {
        super.onCleared()
        ioScope.coroutineContext.cancel()
    }
    // todo
//    suspend fun users(page: Int?) =
//            dataRepository.users(page)

}