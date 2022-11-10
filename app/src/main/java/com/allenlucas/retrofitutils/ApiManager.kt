package com.allenlucas.retrofitutils

import com.allenlucas.retrofit.BaseApiManager

class ApiManager private constructor() : BaseApiManager() {

    companion object {
        val instance by lazy { ApiManager() }
    }

    fun initOkHttpClient(){
        okHttpClient.timeOptions.apply {
            readTimeout(5)
            connectTimeout(5)
            callTimeout(5)
            writeTimeout(5)
        }
    }

    override fun getBaseUrl() = "https://www.wanandroid.com/"
}