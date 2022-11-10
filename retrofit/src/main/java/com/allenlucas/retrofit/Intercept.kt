package com.allenlucas.retrofit

interface DataIntercept {
    /**
     * 判断方法
     * @param code 网络请求返回的code
     * @param message 网络请求返回的message
     * @return 是否需要拦截
     */
    fun intercept(code: Int, message: String): Boolean
}

class DataInterceptManager private constructor() {
    companion object {
        val Instance by lazy { DataInterceptManager() }
    }

    private val intercepts = mutableMapOf<Int, DataIntercept>()

    /**
     * 添加网络请求结果拦截
     */
    fun addDataIntercept(code: Int, intercept: DataIntercept) {
        if (intercepts.containsKey(code)) return
        intercepts[code] = intercept
    }

    /**
     * 获取网络请求结果拦截器
     */
    fun getDataIntercept(code: Int): DataIntercept? = intercepts[code]
}