package com.allenlucas.retrofit.options

import com.allenlucas.retrofit.ContextHelper
import com.allenlucas.retrofit.utils.isNetworkConnected
import okhttp3.Cache
import okhttp3.Interceptor
import java.io.File

/**
 * 网络请求缓存配置
 * https://juejin.cn/post/6845166891476992008
 */
class ApiCacheOptions {

    var onlineCacheTime = 0 // 在线时候的缓存过期时间，如果不想要缓存，直接时间设置为0

    var offlineCacheTime = 60 // 离线时候的缓存过期时间

    var cacheFile = File(ContextHelper.instance.getAppContext().cacheDir, "okHttpCache")   //缓存路径

    var cacheSize: Long = 10 * 1024 * 1024  // 缓存大小


    /**
     * 有网时的缓存处理
     */
    fun netResponse(chain: Interceptor.Chain) = chain.proceed(chain.request()).newBuilder()
        .header("Cache-Control", "public, max-age=$onlineCacheTime")
        .removeHeader("Pragma")
        .build()

    /**
     * 无网时的网络处理
     */
    fun offlineResponse(chain: Interceptor.Chain) =
        if (ContextHelper.instance.getAppContext().isNetworkConnected())
            chain.proceed(chain.request())
        else
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$offlineCacheTime")
                    .build()
            )

    /**
     * 获取设置好的缓存
     */
    fun getCache(): Cache = Cache(cacheFile, cacheSize)
}