package com.allenlucas.retrofitutils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BaseViewModel : ViewModel() {

    private val apiManager by lazy { ApiManager.instance }
    private val api by lazy { apiManager.create(IApi::class.java) }

    fun getBanner() {
        viewModelScope.launch {
            val res = apiManager.callHandler(ResponseResult()) { api.getBanner() }
            Log.e("lal", "code:${res.errorCode},msg:${res.errorMsg}")
            Log.e("lal","code1:${res.code},msg1:${res.msg},time:${System.currentTimeMillis()}")
            res.data?.forEach {
                Log.e("lal-->获取的banner遍历", "title:${it.title}")
            }
        }
    }

}