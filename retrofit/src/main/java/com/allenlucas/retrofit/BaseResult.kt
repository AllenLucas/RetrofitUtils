package com.allenlucas.retrofit

/**
 * 封装的返回接口，包含返回的参数
 */
interface ResultData<T> {
    var code: Int
    var message: String
    var data: T?
    // 实现自己的判断是否成功的逻辑
    fun isSuccess(): Boolean
}

/**
 * 网络请求失败的对象
 */
data class NetError<T>(
    override var code: Int, override var message: String,
    override var data: T? = null
) : ResultData<T> {
    override fun isSuccess(): Boolean = false
}