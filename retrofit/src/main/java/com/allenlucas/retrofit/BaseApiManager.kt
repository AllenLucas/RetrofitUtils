package com.allenlucas.retrofit

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络请求管理
 */
abstract class BaseApiManager {

    abstract fun getBaseUrl(): String

    val okHttpClient = BaseOkHttpClient()

    protected val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okHttpClient.getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    /**
     * 网络
     * @param empty 空返回
     * @param call 处理网络请求的协程
     */
    suspend inline fun <T, R : BaseResult<T>> callHandler(
        empty: R,
        crossinline call: suspend CoroutineScope.() -> R
    ): R = withContext(Dispatchers.IO) {
        val res: R
        try {
            res = call()
        } catch (e: Throwable) {
            return@withContext ApiException.build(e).toBaseResult(empty)
        }
        return@withContext res
    }
}