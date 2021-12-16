package com.allenlucas.retrofit

import com.allenlucas.retrofit.options.ApiCacheOptions
import com.allenlucas.retrofit.options.ApiTimeOptions
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * okHttpClient封装类
 */
class BaseOkHttpClient {

    /**
     * 如果想设置生效，需要在生成retrofit之前设置
     */
    private var okHttpClient: OkHttpClient? = null

    var timeOptions = ApiTimeOptions()  // 设置超时时间

    var cacheOptions = ApiCacheOptions() //缓存类型

    val interceptors = arrayListOf<Interceptor>() // 拦截器添加

    private fun getDefaultClient(): OkHttpClient = OkHttpClient.Builder()
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
        ).addNetworkInterceptor { cacheOptions.netResponse(it) }
        .addInterceptor { cacheOptions.offlineResponse(it) }
        .cache(cacheOptions.getCache())
        .apply {
            if (BuildConfig.DEBUG) addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            interceptors.forEach { addInterceptor(it) }
        }
        .build()

    /**
     * 获取client
     */
    fun getClient() = okHttpClient ?: getDefaultClient()

}