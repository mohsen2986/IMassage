package com.imassage.ui.base

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

fun <T> lazyDeferred(block :suspend CoroutineScope.() ->T ):Lazy<Deferred<T>> {
    return lazy{
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}