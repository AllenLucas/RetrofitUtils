package com.allenlucas.retrofitutils

import com.allenlucas.retrofit.BaseApiManager

class ApiManager private constructor() : BaseApiManager() {

    companion object {
        val instance by lazy { ApiManager() }
    }

    override fun getBaseUrl() = "https://www.wanandroid.com/"
}