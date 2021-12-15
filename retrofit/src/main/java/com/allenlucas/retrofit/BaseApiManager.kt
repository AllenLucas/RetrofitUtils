package com.allenlucas.retrofit

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 网络请求管理
 */
abstract class BaseApiManager {

    abstract fun getBaseUrl(): String

    protected val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(getOkhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    var timeOptions = ApiTimeOptions()  // 设置超时时间

    // 获取okhttp client
    private fun getOkhttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(
                timeOptions.timeOutOptions.readTimeOut,
                timeOptions.timeOutOptions.readTimeUnit
            )
            .connectTimeout(
                timeOptions.timeOutOptions.connectTimeOut,
                timeOptions.timeOutOptions.connectTimeUnit
            )
            .callTimeout(
                timeOptions.timeOutOptions.callTimeOut,
                timeOptions.timeOutOptions.connectTimeUnit
            )
            .writeTimeout(
                timeOptions.timeOutOptions.writeTimeOut,
                timeOptions.timeOutOptions.writeTimeUnit
            ).apply {
                if (BuildConfig.DEBUG) addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
            }
            .build()

    fun <T> create(serviceClass: Class<T>) = retrofit.create(serviceClass)

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