package com.allenlucas.retrofit

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络请求管理
 */
abstract class BaseApiManager {

    abstract fun getBaseUrl(): String
    private val retrofitMap = hashMapOf<String, Retrofit>() //支持多baseUrl切换的map
    protected val okHttpClient = BaseOkHttpClient() //默认okHttpClient配置

    init {
        retrofitMap[getBaseUrl()] = getRetrofit()  //默认会将初始化的baseUrl创建的retrofit放入map内
    }

    /**
     * 根据传入url及client创建retrofit
     */
    private fun getRetrofit(
        baseUrl: String = getBaseUrl(),
        client: BaseOkHttpClient = okHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client.getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>,baseUrl: String = getBaseUrl()): T {
        var retrofit: Retrofit? = retrofitMap[baseUrl]
        if (null == retrofit) {
            retrofit = getRetrofit(baseUrl)
            retrofitMap[baseUrl] = retrofit
        }
        return retrofit.create(serviceClass)
    }

    /**
     * 网络
     * @param call 处理网络请求的协程
     */
    suspend inline fun <T> callHandler(
        crossinline call: suspend CoroutineScope.() -> ResultData<T>
    ): ResultData<T> = withContext(Dispatchers.IO) {
        val res = try {
            call()
        } catch (e: Throwable) {
            ApiException.build(e).toResponse()
        }
        if (true ==
            DataInterceptManager.Instance
                .getDataIntercept(res.code)?.intercept(res.code, res.message)
        ) {
            cancel()
        }
        return@withContext res
    }
}