package com.allenlucas.retrofit

/**
 * 封装基类返回数据
 */
abstract class BaseResult<T> {

    var code: Int = -1
    var msg: String = ""
    var data: T? = null

    // 是否成功
    abstract fun isSuccess(): Boolean

    // 获取返回数据
    abstract fun getRequestData(): T?

    // 获取返回信息
    abstract fun getRequestMsg(): String
}